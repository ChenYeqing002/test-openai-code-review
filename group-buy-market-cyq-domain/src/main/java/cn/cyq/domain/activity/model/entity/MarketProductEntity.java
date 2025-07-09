package cn.cyq.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 营销商品
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketProductEntity {
    /** 活动ID */
    private Long activityId;
    // 用户id
    private String userId;
    // 商品id
    private String goodsId;
    // 来源
    private String source;
    // 渠道
    private String channel;
}
