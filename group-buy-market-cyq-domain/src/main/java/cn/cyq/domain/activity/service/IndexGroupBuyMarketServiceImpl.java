package cn.cyq.domain.activity.service;

import cn.cyq.domain.activity.model.entity.MarketProductEntity;
import cn.cyq.domain.activity.model.entity.TrialBalanceEntity;
import cn.cyq.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import cn.cyq.types.design.framework.tree.StrategyHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IndexGroupBuyMarketServiceImpl implements IIndexGroupBuyMarketService {

    @Resource
    private DefaultActivityStrategyFactory defaultActivityStrategyFactory;

    @Override
    public TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception {
        // 获取具体的策略处理器
        StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> strategyHandler =
                defaultActivityStrategyFactory.strategyHandler();

        // 通过该处理器来执行试算操作 目前返回 RootNode，并执行apply方法
        TrialBalanceEntity trialBalanceEntity =
                strategyHandler.apply(marketProductEntity, new DefaultActivityStrategyFactory.DynamicContext());

        return trialBalanceEntity;
    }
}
