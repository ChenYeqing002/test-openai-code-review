package cn.cyq.domain.activity.adapter.repository;

import cn.cyq.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import cn.cyq.domain.activity.model.valobj.SkuVO;

public interface IActivityRepository {

    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(String source, String channel);

    SkuVO querySkuByGoodsId(String goodsId);

}
