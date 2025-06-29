package cn.cyq.domain.activity.service.trial.node;

import cn.cyq.domain.activity.model.entity.MarketProductEntity;
import cn.cyq.domain.activity.model.entity.TrialBalanceEntity;
import cn.cyq.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import cn.cyq.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import cn.cyq.types.design.framework.tree.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 策略树根节点
 */
@Slf4j
@Service("rootNode")
public class RootNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private SwitchRoot switchRoot;

    /**
     * 处理当前节点的业务
     * @param requestParameter
     * @param dynamicContext
     * @return
     * @throws Exception
     */
    @Override
    public TrialBalanceEntity apply(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("处理根节点业务");
        return this.router(requestParameter, dynamicContext);
    }

    /**
     * 获取下一个节点
     * @param requestParameter
     * @param dynamicContext
     * @return
     */
    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) {
        return switchRoot;
    }
}
