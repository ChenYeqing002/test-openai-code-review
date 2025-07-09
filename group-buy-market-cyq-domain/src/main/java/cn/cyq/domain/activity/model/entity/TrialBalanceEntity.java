package cn.cyq.domain.activity.model.entity;

import cn.cyq.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrialBalanceEntity {
    // 商品id
    private String goodsId;
    // 商品名称
    private String goodsName;
    // 原价
    private BigDecimal originalPrice;
    // 折扣价
    private BigDecimal deductionPrice;
    // 拼团目标人数
    private Integer targetCount;
    // 拼团开始时间
    private LocalDateTime startTime;
    // 拼团结束时间
    private LocalDateTime endTime;
    // 是否可见
    private Boolean isVisible;
    // 是否可以参与
    private Boolean isEnable;
    // 活动配置信息
    private GroupBuyActivityDiscountVO groupBuyActivityDiscountVO;
}
