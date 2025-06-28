package cn.cyq.infrastructure.dao.po;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author 陈烨庆
 * @since 2025-06-28 23:26:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupBuyDiscount implements Serializable{

    private static final long serialVersionUID=1L;

    /**
     *  自增ID
     */
    private Long id;

    /**
     *  折扣ID
     */
    private Integer discountId;

    /**
     *  折扣标题
     */
    private String discountName;

    /**
     *  折扣描述
     */
    private String discountDesc;

    /**
     *  折扣类型（0:BASE、1:TAG）
     */
    private Integer discountType;

    /**
     *  营销优惠计划（ZJ:直减、MJ:满减、N元购）
     */
    private String marketPlan;

    /**
     *  营销优惠表达式
     */
    private String marketExpr;

    /**
     *  人群标签，特定优惠限定
     */
    private String tagId;

    /**
     *  创建时间
     */
    private Date createTime;

    /**
     *  更新时间
     */
    private Date updateTime;


}