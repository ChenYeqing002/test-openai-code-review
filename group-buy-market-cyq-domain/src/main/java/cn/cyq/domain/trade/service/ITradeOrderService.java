package cn.cyq.domain.trade.service;

import cn.cyq.domain.trade.model.eneity.MarketPayOrderEntity;
import cn.cyq.domain.trade.model.eneity.PayActivityEntity;
import cn.cyq.domain.trade.model.eneity.PayDiscountEntity;
import cn.cyq.domain.trade.model.eneity.UserEntity;
import cn.cyq.domain.trade.model.valobj.GroupBuyProgressVO;

public interface ITradeOrderService {

    MarketPayOrderEntity queryNoPayMarketPayOrderByOutTradeNo(String userId, String outTradeNo);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    MarketPayOrderEntity lockMarketPayOrder(UserEntity userEntity, PayActivityEntity payActivityEntity, PayDiscountEntity payDiscountEntity);
}
