package cn.cyq.types.design.framework.tree;

/**
 * 策略映射器
 * @param <T> 入参
 * @param <D> 上下文
 * @param <R> 出参
 */
public interface StrategyMapper<T, D, R> {

    /**
     * 获取待执行策略
     * 用于获取每一个要执行的节点，相当于一个流程走完进入到下一个流程的过程
     * @param requestParameter 入参
     * @param dynamicContext 上下文
     * @return 出参
     */
    StrategyHandler<T, D, R> get(T requestParameter, D dynamicContext);
}
