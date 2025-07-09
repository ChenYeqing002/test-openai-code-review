package cn.cyq.infrastructure.adapter.repository;

import cn.cyq.domain.trade.adapter.repository.ITradeRepository;
import cn.cyq.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import cn.cyq.domain.trade.model.eneity.MarketPayOrderEntity;
import cn.cyq.domain.trade.model.eneity.PayActivityEntity;
import cn.cyq.domain.trade.model.eneity.PayDiscountEntity;
import cn.cyq.domain.trade.model.eneity.UserEntity;
import cn.cyq.domain.trade.model.valobj.GroupBuyProgressVO;
import cn.cyq.domain.trade.model.valobj.TradeOrderStatusEnumVO;
import cn.cyq.infrastructure.dao.IGroupBuyOrderDao;
import cn.cyq.infrastructure.dao.IGroupBuyOrderListDao;
import cn.cyq.infrastructure.dao.po.GroupBuyOrder;
import cn.cyq.infrastructure.dao.po.GroupBuyOrderList;
import cn.cyq.types.enums.ResponseCode;
import cn.cyq.types.exception.AppException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Repository
public class TradeRepository implements ITradeRepository {

    @Resource
    private IGroupBuyOrderDao groupBuyOrderDao;

    @Resource
    private IGroupBuyOrderListDao groupBuyOrderListDao;

    @Override
    public MarketPayOrderEntity queryNoPayMarketPayOrderByOutTradeNo(String userId, String outTradeNo) {
        GroupBuyOrderList groupBuyOrderListReq = new GroupBuyOrderList();
        groupBuyOrderListReq.setUserId(userId);
        groupBuyOrderListReq.setOutTradeNo(outTradeNo);

        GroupBuyOrderList groupBuyOrderList = groupBuyOrderListDao.queryGroupBuyOrderRecordByOutTradeNo(groupBuyOrderListReq);

        if (null == groupBuyOrderList) {
            return null;
        }

        return MarketPayOrderEntity.builder()
                .orderId(groupBuyOrderList.getOrderId())
                .deductionPrice(groupBuyOrderList.getDeductionPrice())
                .tradeOrderStatusEnumVO(TradeOrderStatusEnumVO.valueOf(groupBuyOrderList.getStatus()))
                .build();
    }

    @Override
    public GroupBuyProgressVO queryGroupBuyProgress(String teamId) {
        GroupBuyOrder groupBuyOrder = groupBuyOrderDao.queryGroupBuyProgress(teamId);
        if (null == groupBuyOrder) {
            return null;
        }

        return GroupBuyProgressVO.builder()
                .targetCount(groupBuyOrder.getTargetCount())
                .completeCount(groupBuyOrder.getCompleteCount())
                .lockCount(groupBuyOrder.getLockCount())
                .build();
    }

    @Transactional(timeout = 500)
    @Override
    public MarketPayOrderEntity lockMarketPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate) {
        UserEntity userEntity = groupBuyOrderAggregate.getUserEntity();
        PayActivityEntity payActivityEntity = groupBuyOrderAggregate.getPayActivityEntity();
        PayDiscountEntity payDiscountEntity = groupBuyOrderAggregate.getPayDiscountEntity();

        String teamId = payActivityEntity.getTeamId();
        if (StringUtils.isBlank(teamId)) {
            teamId = RandomStringUtils.randomNumeric(8);
            // 首次拼团 构建拼团订单
            GroupBuyOrder groupBuyOrder = GroupBuyOrder.builder()
                    .teamId(teamId)
                    .activityId(payActivityEntity.getActivityId())
                    .source(payDiscountEntity.getSource())
                    .channel(payDiscountEntity.getChannel())
                    .originalPrice(payDiscountEntity.getOriginalPrice())
                    .deductionPrice(payDiscountEntity.getDeductionPrice())
                    .payPrice(payDiscountEntity.getOriginalPrice().subtract(payDiscountEntity.getDeductionPrice()))
                    .targetCount(payActivityEntity.getTargetCount())
                    .completeCount(0)
                    .lockCount(1)
                    .status(0)
                    .build();
            groupBuyOrderDao.insert(groupBuyOrder);
        } else {
            int updateAddLockCount = groupBuyOrderDao.updateAddLockCount(teamId);
            if (updateAddLockCount != 1) {
                throw new AppException(ResponseCode.E0005);
            }
        }

        // 更新拼团明细
        String orderId = RandomStringUtils.randomNumeric(12);
        GroupBuyOrderList groupBuyOrderList = GroupBuyOrderList.builder()
                .userId(userEntity.getUserId())
                .teamId(teamId)
                .orderId(orderId)
                .activityId(payActivityEntity.getActivityId())
                .startTime(payActivityEntity.getStartTime())
                .endTime(payActivityEntity.getEndTime())
                .goodsId(payDiscountEntity.getGoodsId())
                .source(payDiscountEntity.getSource())
                .channel(payDiscountEntity.getChannel())
                .originalPrice(payDiscountEntity.getOriginalPrice())
                .deductionPrice(payDiscountEntity.getDeductionPrice())
                .status(TradeOrderStatusEnumVO.CREATE.getCode())
                .outTradeNo(payDiscountEntity.getOutTradeNo())
                .build();
        try {
            groupBuyOrderListDao.insert(groupBuyOrderList);
        } catch (DuplicateKeyException e) {
            throw new AppException(ResponseCode.INDEX_EXCEPTION);
        }

        return MarketPayOrderEntity.builder()
                .orderId(orderId)
                .deductionPrice(payDiscountEntity.getDeductionPrice())
                .tradeOrderStatusEnumVO(TradeOrderStatusEnumVO.CREATE)
                .build();
    }
}
