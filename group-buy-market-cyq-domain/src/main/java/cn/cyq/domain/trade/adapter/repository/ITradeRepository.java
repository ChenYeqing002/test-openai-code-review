package cn.cyq.domain.trade.adapter.repository;

import cn.cyq.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import cn.cyq.domain.trade.model.eneity.MarketPayOrderEntity;
import cn.cyq.domain.trade.model.valobj.GroupBuyProgressVO;

public interface ITradeRepository {
    MarketPayOrderEntity queryNoPayMarketPayOrderByOutTradeNo(String userId, String outTradeNo);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    MarketPayOrderEntity lockMarketPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate);
}
