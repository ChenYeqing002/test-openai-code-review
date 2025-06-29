package cn.cyq.test.domain.trial;

import cn.cyq.domain.activity.model.entity.MarketProductEntity;
import cn.cyq.domain.activity.model.entity.TrialBalanceEntity;
import cn.cyq.domain.activity.service.IIndexGroupBuyMarketService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexGroupBuyMarketServiceTest {

    @Resource
    private IIndexGroupBuyMarketService indexGroupBuyMarketService;

    @Test
    public void test01() throws Exception {
        MarketProductEntity marketProductEntity = MarketProductEntity.builder().build();
        TrialBalanceEntity trialBalanceEntity = indexGroupBuyMarketService.indexMarketTrial(marketProductEntity);
        log.info("trial:{}", JSON.toJSONString(trialBalanceEntity));
    }

}
