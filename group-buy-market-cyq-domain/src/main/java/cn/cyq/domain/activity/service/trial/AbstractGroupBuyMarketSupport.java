package cn.cyq.domain.activity.service.trial;

import cn.cyq.domain.activity.adapter.repository.IActivityRepository;
import cn.cyq.types.design.framework.tree.AbstractMultiThreadStrategyRouter;
import cn.cyq.types.design.framework.tree.AbstractStrategyRouter;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * 拼团活动服务支撑类
 * @param <MarketProductEntity>
 * @param <DynamicContext>
 * @param <TrialBalanceEntity>
 */
public abstract class AbstractGroupBuyMarketSupport<MarketProductEntity, DynamicContext, TrialBalanceEntity> extends AbstractMultiThreadStrategyRouter<MarketProductEntity, DynamicContext, TrialBalanceEntity> {

    protected long timeout = 500;

    @Resource
    protected IActivityRepository repository;

    @Override
    protected void multiThread(MarketProductEntity requestParameter, DynamicContext dynamicContext) throws Exception {

    }
}
