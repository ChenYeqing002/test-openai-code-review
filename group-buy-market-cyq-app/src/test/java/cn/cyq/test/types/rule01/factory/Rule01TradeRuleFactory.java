package cn.cyq.test.types.rule01.factory;

import cn.cyq.test.types.rule01.logic.RuleLogic101;
import cn.cyq.test.types.rule01.logic.RuleLogic102;
import cn.cyq.types.design.framework.link.model1.ILogicLink;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Rule01TradeRuleFactory {

    @Resource
    private RuleLogic101 ruleLogic101;

    @Resource
    private RuleLogic102 ruleLogic102;

    public ILogicLink<String, JSONObject, String> openLogicLink() {
        ruleLogic101.appendNext(ruleLogic102);
        return ruleLogic101;
    }
}
