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
import java.util.BitSet;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ITagServiceTest {

    @Resource
    private ITagService tagService;

    @Resource
    private IRedisService redisService;

    @Test
    public void test_bigset() {
        // 获取BitSet
        RBitSet mon = redisService.getBitSet("mon");
        mon.set(3, true);
        mon.set(5, true);
        mon.set(7, true);

        System.out.println("位操作前:" + mon.get(3));
        System.out.println("位操作前:" + mon.get(5));
        System.out.println("位操作前:" + mon.get(7));
        System.out.println("位操作前:" + mon.get(8));

        RBitSet thur = redisService.getBitSet("thur");
        thur.set(3, true);
        thur.set(5, true);
        thur.set(8, true);

        RBitSet wen = redisService.getBitSet("wen");
        wen.set(3, true);
        wen.set(4, true);
        wen.set(6, true);

        mon.and("thur", "wen");

        System.out.println("位操作后:" + mon.get(3));
        System.out.println("位操作后:" + mon.get(5));
        System.out.println("位操作后:" + mon.get(7));
        System.out.println("位操作后:" + mon.get(8));
    }

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
