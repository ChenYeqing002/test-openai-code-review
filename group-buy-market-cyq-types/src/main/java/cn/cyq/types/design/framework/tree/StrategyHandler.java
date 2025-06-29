package cn.cyq.types.design.framework.tree;

/**
 * 策略处理器
 * @param <T> 入参
 * @param <D> 上下文
 * @param <R> 出参
 */
public interface StrategyHandler<T, D, R> {

    StrategyHandler DEFAULT = (T, D) -> null;

    /**
     * 受理执行的业务流程。
     * 每个业务流程执行时，如果有数据是从前面节点到后面节点要使用的，那么可以填充到 dynamicContext 中
     * @param requestParameter
     * @param dynamicContext
     * @return
     * @throws Exception
     */
    R apply(T requestParameter, D dynamicContext) throws Exception;
}
