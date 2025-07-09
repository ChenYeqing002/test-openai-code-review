package cn.cyq.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SCSkuActivity {

    private Long id;
    /** 来源 */
    private String source;
    /** 渠道 */
    private String channel;
    /** 活动ID */
    private Long activityId;
    /** 商品ID */
    private String goodsId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
