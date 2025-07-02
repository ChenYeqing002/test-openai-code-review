package cn.cyq.test.domain.tag;

import cn.cyq.domain.tag.service.ITagService;
import cn.cyq.infrastructure.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBitSet;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ITagServiceTest {

    @Resource
    private ITagService tagService;

    @Resource
    private IRedisService redisService;

    @Test
    public void test_tagJob() {
        tagService.execTagBatchJob("RQ_KJHKL98UU78H66554GFDV", "10001");
    }

    @Test
    public void test_getTagBigMap() {
        RBitSet bitSet = redisService.getBitSet("RQ_KJHKL98UU78H66554GFDV");
        log.info("测试结果:{}", redisService.getIndexFromUserId2("zhaohaizhu"));
        log.info("测试结果:{}", bitSet.get(redisService.getIndexFromUserId2("zhaohaizhu")));
        log.info("测试结果:{}", bitSet.get(redisService.getIndexFromUserId2("zhangsan")));
        log.info("测试结果:{}", bitSet.get(redisService.getIndexFromUserId2("baishazi")));
    }
}
