package cn.cyq.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TagScopeEnumVO {
    VISIBLE(true, false, "是否可看见拼团"),
    ENABLE(true, false, "是否可参与拼团"),
    ;

    private Boolean allow;
    private Boolean refuse;
    private String desc;
}
