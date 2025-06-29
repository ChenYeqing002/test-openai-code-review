package cn.cyq.domain.activity.service.trial.thread;

import cn.cyq.domain.activity.adapter.repository.IActivityRepository;
import cn.cyq.domain.activity.model.valobj.SkuVO;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class QuerySkuVOFromDBThreadTask implements Callable<SkuVO> {

    private final String goodsId;
    private final IActivityRepository repository;

    @Override
    public SkuVO call() throws Exception {
        return repository.querySkuByGoodsId(goodsId);
    }
}
