package cn.cyq.domain.trade.model.eneity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayActivityEntity {

    /** 拼单组队ID */
    private String teamId;
    /** 活动ID */
    private Long activityId;
    /** 活动名称 */
    private String activityName;
    /** 活动开始时间 */
    private LocalDateTime startTime;
    /** 活动结束时间 */
    private LocalDateTime endTime;
    /** 目标数量 */
    private Integer targetCount;
}
