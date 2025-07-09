package cn.cyq.domain.trade.service;

import cn.cyq.domain.trade.adapter.repository.ITradeRepository;
import cn.cyq.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import cn.cyq.domain.trade.model.eneity.MarketPayOrderEntity;
import cn.cyq.domain.trade.model.eneity.PayActivityEntity;
import cn.cyq.domain.trade.model.eneity.PayDiscountEntity;
import cn.cyq.domain.trade.model.eneity.UserEntity;
import cn.cyq.domain.trade.model.valobj.GroupBuyProgressVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class TradeOrderService implements ITradeOrderService {

    @Resource
    private ITradeRepository repository;

    @Override
    public MarketPayOrderEntity queryNoPayMarketPayOrderByOutTradeNo(String userId, String outTradeNo) {
        return repository.queryNoPayMarketPayOrderByOutTradeNo(userId, outTradeNo);
    }

    @Override
    public GroupBuyProgressVO queryGroupBuyProgress(String teamId) {
        return repository.queryGroupBuyProgress(teamId);
    }

    @Override
    public MarketPayOrderEntity lockMarketPayOrder(UserEntity userEntity, PayActivityEntity payActivityEntity, PayDiscountEntity payDiscountEntity) {
        GroupBuyOrderAggregate groupBuyOrderAggregate = GroupBuyOrderAggregate.builder()
                .userEntity(userEntity)
                .payActivityEntity(payActivityEntity)
                .payDiscountEntity(payDiscountEntity)
                .build();
        return repository.lockMarketPayOrder(groupBuyOrderAggregate);
    }
}
