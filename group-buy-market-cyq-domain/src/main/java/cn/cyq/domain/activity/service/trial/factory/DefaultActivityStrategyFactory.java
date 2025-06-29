package cn.cyq.domain.activity.service.trial.factory;

import cn.cyq.domain.activity.model.entity.MarketProductEntity;
import cn.cyq.domain.activity.model.entity.TrialBalanceEntity;
import cn.cyq.domain.activity.service.trial.node.RootNode;
import cn.cyq.types.design.framework.tree.StrategyHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 策略工厂类
 * 提供了 RootNode 策略执行器
 */
@Service
public class DefaultActivityStrategyFactory {

    private final RootNode rootNode;

    public DefaultActivityStrategyFactory(RootNode rootNode) {
        this.rootNode = rootNode;
    }

    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> strategyHandler() {
        return rootNode;
    }

    // 动态上下文 串联功能节点的数据
    @Data
    @Builder
    @NoArgsConstructor
    public static class DynamicContext {}
}
