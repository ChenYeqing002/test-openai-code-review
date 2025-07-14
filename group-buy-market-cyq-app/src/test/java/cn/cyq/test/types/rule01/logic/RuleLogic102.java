package cn.cyq.test.types.rule01.logic;

import cn.cyq.types.design.framework.link.model1.AbstractLogicLink;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RuleLogic102 extends AbstractLogicLink<String, JSONObject, String> {
    @Override
    public String apply(String requestParameter, JSONObject dynamicContext) throws Exception {
        log.info("link model01 RuleLogic102");
        dynamicContext.put("age", "25");
        return "link model01 单实例链" + JSON.toJSONString(dynamicContext);
    }
}
