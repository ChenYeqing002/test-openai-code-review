package cn.cyq.domain.activity.service.trial.node;

import cn.cyq.domain.activity.model.entity.MarketProductEntity;
import cn.cyq.domain.activity.model.entity.TrialBalanceEntity;
import cn.cyq.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import cn.cyq.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import cn.cyq.types.design.framework.tree.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service("marketNode")
public class MarketNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private EndNode endNode;

    @Override
    public TrialBalanceEntity apply(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("处理营销优惠节点业务");
        return this.router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) {
        return endNode;
    }
}
