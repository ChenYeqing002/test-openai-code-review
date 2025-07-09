package cn.cyq.api.dto;

import lombok.Data;

@Data
public class LockMarketPayOrderRequestDTO {
    private String userId;
    /** 拼单组队ID */
    private String teamId;
    /** 活动ID */
    private Long activityId;
    /** 来源 */
    private String source;
    /** 渠道 */
    private String channel;
    /** 商品ID */
    private String goodsId;
    /** 外部交易单号-确保外部调用唯一幂等 */
    private String outTradeNo;
}
