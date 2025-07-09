package cn.cyq.infrastructure.dao.po;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 拼团活动
 *
 * @author 陈烨庆
 * @since 2025-06-28 23:17:35
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupBuyActivity implements Serializable{

    private static final long serialVersionUID= 5403012882454975344L;
    /** 自增 */
    private Long id;
    /** 活动ID */
    private Long activityId;
    /** 活动名称 */
    private String activityName;
    /** 折扣ID */
    private String discountId;
    /** 拼团方式（0自动成团、1达成目标拼团） */
    private Integer groupType;
    /** 拼团次数限制 */
    private Integer takeLimitCount;
    /** 拼团目标 */
    private Integer target;
    /** 拼团时长（分钟） */
    private Integer validTime;
    /** 活动状态（0创建、1生效、2过期、3废弃） */
    private Integer status;
    /** 活动开始时间 */
    private LocalDateTime startTime;
    /** 活动结束时间 */
    private LocalDateTime endTime;
    /** 人群标签规则标识 */
    private String tagId;
    /** 人群标签规则范围（多选；1可见限制、2参与限制） */
    private String tagScope;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 更新时间 */
    private LocalDateTime updateTime;


}