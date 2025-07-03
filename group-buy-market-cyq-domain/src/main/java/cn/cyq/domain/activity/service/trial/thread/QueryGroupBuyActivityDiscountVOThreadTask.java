package cn.cyq.domain.activity.service.trial.thread;

import cn.cyq.domain.activity.adapter.repository.IActivityRepository;
import cn.cyq.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import cn.cyq.domain.activity.model.valobj.SCSkuActivityVO;
import lombok.RequiredArgsConstructor;

import javax.annotation.Resource;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class QueryGroupBuyActivityDiscountVOThreadTask implements Callable<GroupBuyActivityDiscountVO> {

    private final String source;
    private final String channel;
    private final String goodsId;
    private final IActivityRepository repository;

    @Override
    public GroupBuyActivityDiscountVO call() throws Exception {
        SCSkuActivityVO scSkuActivity = repository.querySCSkuActivityBySCGoodsId(source, channel, goodsId);
        if (scSkuActivity == null) {
            return null;
        }
        Long activityId = scSkuActivity.getActivityId();
        return repository.queryGroupBuyActivityDiscountVO(activityId);
    }
}
