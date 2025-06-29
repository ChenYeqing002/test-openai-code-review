package cn.cyq.domain.activity.service.trial.thread;

import cn.cyq.domain.activity.adapter.repository.IActivityRepository;
import cn.cyq.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import lombok.RequiredArgsConstructor;

import javax.annotation.Resource;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class QueryGroupBuyActivityDiscountVOThreadTask implements Callable<GroupBuyActivityDiscountVO> {

    private final String source;
    private final String channel;
    private final IActivityRepository repository;

    @Override
    public GroupBuyActivityDiscountVO call() throws Exception {
        return repository.queryGroupBuyActivityDiscountVO(source, channel);
    }
}
