package cn.cyq.test.infrastructure.dao;

import cn.cyq.infrastructure.dao.IGroupBuyActivityDao;
import cn.cyq.infrastructure.dao.IGroupBuyDiscountDao;
import cn.cyq.infrastructure.dao.po.GroupBuyActivity;
import cn.cyq.infrastructure.dao.po.GroupBuyDiscount;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupBuyDiscountDaoTest {

    @Resource
    private IGroupBuyDiscountDao groupBuyDiscountDao;

    @Test
    public void test_groupBuyActivityDao() {
        List<GroupBuyDiscount> groupBuyActivities =
                groupBuyDiscountDao.queryGroupBuyDiscountList();

        log.info("测试结果:{}", JSON.toJSONString(groupBuyActivities));
    }
}
