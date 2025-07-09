package cn.cyq.domain.trade.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询进度
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupBuyProgressVO {

    private Integer targetCount;
    private Integer completeCount;
    private Integer lockCount;
}
