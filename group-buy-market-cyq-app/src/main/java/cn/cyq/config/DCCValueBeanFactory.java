package cn.cyq.config;

import cn.cyq.types.annotations.DCCValue;
import cn.cyq.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
public class DCCValueBeanFactory implements BeanPostProcessor {

    private static final String BASE_CONFIG_PATH = "group_buy_market_doc_";

    private final RedissonClient redissonClient;

    private final Map<String, Object> dccObjectMap = new HashMap<>();

    public DCCValueBeanFactory(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Bean("dccTopic")
    public RTopic dccRedisTopicListener(RedissonClient redissonClient) {
        RTopic topic = redissonClient.getTopic("group_buy_market_doc");
        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence charSequence, String s) {
                // 1. 获取要设置的属性和值
                String[] split = s.split(Constants.SPLIT);
                String attribute = split[0];
                String key = BASE_CONFIG_PATH.concat(attribute);
                String value = split[1];

                // 2. 更新缓存中key的值
                RBucket<Object> bucket = redissonClient.getBucket(key);
                boolean exists = bucket.isExists();
                if (!exists) {
                    return;
                }
                bucket.set(value);

                // 3. 获取原始的类
                Object objBean = dccObjectMap.get(key);
                if (null == objBean) {
                    return;
                }

                Class<?> objBeanClass = objBean.getClass();
                if (AopUtils.isAopProxy(objBeanClass)) {
                    objBeanClass = AopUtils.getTargetClass(objBean);
                }

                // 4. 通过反射把类中的属性更新
                try {
                    Field field = objBeanClass.getDeclaredField(attribute);

                    field.setAccessible(true);
                    field.set(objBean, value);
                    field.setAccessible(false);
                    log.info("DCC 节点监听，动态设置值 {} {}", key, value);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return topic;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        // 1. 获取原始类信息
        Class<?> targetBeanClass = bean.getClass();
        // 2. 获取原始对象
        Object targetBeanObject = bean;

        // 判断是否为代理对象
        if (AopUtils.isAopProxy(bean)) {
            targetBeanClass = AopUtils.getTargetClass(bean);
            targetBeanObject = AopProxyUtils.getSingletonTarget(bean);
        }

        // 3. 获取类中对应的属性信息
        Field[] fields = targetBeanClass.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(DCCValue.class)) {
                continue;
            }

            // 4. 获取@DCCValue的内容
            DCCValue dccValue = field.getAnnotation(DCCValue.class);

            // 获取注解中的值信息
            String value = dccValue.value();
            if (StringUtils.isBlank(value)) {
                throw new RuntimeException(field.getName() + " @DCCValue is not config value config case 「isSwitch/isSwitch:1」");
            }

            String[] split = value.split(":");
            String key = BASE_CONFIG_PATH.concat(split[0]);
            String defaultValue = split.length == 2 ? split[1] : null;

            // 把设值暂定为默认值
            String setValue = defaultValue;
            if (StringUtils.isBlank(defaultValue)) {
                throw new RuntimeException("dcc config error " + key + " is not null - 请配置默认值！");
            }

            try {
                // 判断缓存中是否存在key
                RBucket<String> bucket = redissonClient.getBucket(key);
                // 获取缓存中的key，不存在添加key并设置默认值，存在则使用缓存中的值
                boolean exists = bucket.isExists();
                if (!exists) {
                    bucket.set(defaultValue);
                } else {
                    setValue = bucket.get();
                }

                // 通过反射修改属性值
                field.setAccessible(true);
                field.set(targetBeanObject, setValue);
                field.setAccessible(false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // 保存动态配置对象信息
            dccObjectMap.put(key, targetBeanObject);
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
