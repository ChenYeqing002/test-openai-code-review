package cn.cyq.trigger.http;

import cn.cyq.api.IMarketTradeService;
import cn.cyq.api.dto.LockMarketPayOrderRequestDTO;
import cn.cyq.api.dto.LockMarketPayOrderResponseDTO;
import cn.cyq.api.response.Response;
import cn.cyq.domain.activity.model.entity.MarketProductEntity;
import cn.cyq.domain.activity.model.entity.TrialBalanceEntity;
import cn.cyq.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import cn.cyq.domain.activity.service.IIndexGroupBuyMarketService;
import cn.cyq.domain.trade.model.eneity.MarketPayOrderEntity;
import cn.cyq.domain.trade.model.eneity.PayActivityEntity;
import cn.cyq.domain.trade.model.eneity.PayDiscountEntity;
import cn.cyq.domain.trade.model.eneity.UserEntity;
import cn.cyq.domain.trade.model.valobj.GroupBuyProgressVO;
import cn.cyq.domain.trade.service.ITradeOrderService;
import cn.cyq.types.enums.ResponseCode;
import cn.cyq.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/gbm/trade/")
public class MarketTradeController implements IMarketTradeService {

    @Resource
    private IIndexGroupBuyMarketService indexGroupBuyMarketService;

    @Resource
    private ITradeOrderService tradeOrderService;

    @PostMapping("/lockMarketPayOrder")
    @Override
    public Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(@RequestBody LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO) {
        try {
            String userId = lockMarketPayOrderRequestDTO.getUserId();
            String teamId = lockMarketPayOrderRequestDTO.getTeamId();
            Long activityId = lockMarketPayOrderRequestDTO.getActivityId();
            String source = lockMarketPayOrderRequestDTO.getSource();
            String channel = lockMarketPayOrderRequestDTO.getChannel();
            String goodsId = lockMarketPayOrderRequestDTO.getGoodsId();
            String outTradeNo = lockMarketPayOrderRequestDTO.getOutTradeNo();

            log.info("营销交易锁单: {} LockMarketPayOrderRequestDTO: {}", userId, JSON.toJSONString(lockMarketPayOrderRequestDTO));

            if (StringUtils.isBlank(userId) || ObjectUtils.isEmpty(activityId) ||
                    StringUtils.isBlank(source) || StringUtils.isBlank(channel) || StringUtils.isBlank(goodsId) ||
                    StringUtils.isBlank(outTradeNo)) {
                return Response.error(ResponseCode.ILLEGAL_PARAMETER);
            }
            MarketPayOrderEntity marketPayOrderEntity = tradeOrderService.queryNoPayMarketPayOrderByOutTradeNo(userId, outTradeNo);
            if (null != marketPayOrderEntity) {
                LockMarketPayOrderResponseDTO lockMarketPayOrderResponseDTO = LockMarketPayOrderResponseDTO.builder()
                        .orderId(marketPayOrderEntity.getOrderId())
                        .deductionPrice(marketPayOrderEntity.getDeductionPrice())
                        .tradeOrderStatus(marketPayOrderEntity.getTradeOrderStatusEnumVO().getCode())
                        .build();
                log.info("交易锁单记录(存在): {} marketPayOrderEntity: {}", userId, JSON.toJSONString(marketPayOrderEntity));
                return Response.success(lockMarketPayOrderResponseDTO);
            }

            // 锁单之前判断是否已达上限
            if (null != teamId) {
                GroupBuyProgressVO groupBuyProgressVO = tradeOrderService.queryGroupBuyProgress(teamId);

                if (null != groupBuyProgressVO && Objects.equals(groupBuyProgressVO.getTargetCount(), groupBuyProgressVO.getLockCount())) {
                    return Response.error(ResponseCode.E0006);
                }
            }

            // 营销优惠试算
            TrialBalanceEntity trialBalanceEntity = indexGroupBuyMarketService.indexMarketTrial(MarketProductEntity.builder()
                    .userId(userId)
                    .source(source)
                    .channel(channel)
                    .goodsId(goodsId)
                    .activityId(activityId)
                    .build());

            GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = trialBalanceEntity.getGroupBuyActivityDiscountVO();

            // 锁单
            marketPayOrderEntity = tradeOrderService.lockMarketPayOrder(
                    UserEntity.builder().userId(userId).build(),
                    PayActivityEntity.builder()
                            .teamId(teamId)
                            .activityId(activityId)
                            .activityName(groupBuyActivityDiscountVO.getActivityName())
                            .startTime(groupBuyActivityDiscountVO.getStartTime())
                            .endTime(groupBuyActivityDiscountVO.getEndTime())
                            .targetCount(groupBuyActivityDiscountVO.getTarget())
                            .build(),
                    PayDiscountEntity.builder()
                            .source(source)
                            .channel(channel)
                            .goodsId(goodsId)
                            .goodsName(trialBalanceEntity.getGoodsName())
                            .originalPrice(trialBalanceEntity.getOriginalPrice())
                            .deductionPrice(trialBalanceEntity.getDeductionPrice())
                            .payPrice(trialBalanceEntity.getPayPrice())
                            .outTradeNo(outTradeNo)
                            .build()
            );
            return Response.success(LockMarketPayOrderResponseDTO.builder()
                    .orderId(marketPayOrderEntity.getOrderId())
                    .deductionPrice(marketPayOrderEntity.getDeductionPrice())
                    .tradeOrderStatus(marketPayOrderEntity.getTradeOrderStatusEnumVO().getCode())
                    .build());
        } catch (AppException e) {
            return Response.error(e);
        } catch (Exception e) {
            return Response.error(ResponseCode.UN_ERROR);
        }
    }
}
