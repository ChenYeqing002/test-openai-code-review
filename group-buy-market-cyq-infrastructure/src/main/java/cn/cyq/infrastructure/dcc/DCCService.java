package cn.cyq.infrastructure.dcc;

import cn.cyq.types.annotations.DCCValue;
import org.springframework.stereotype.Service;

@Service
public class DCCService {

    /**
     * 降级开关 1:降级 0:不降级
     */
    @DCCValue("downgradeSwitch:0")
    private String downgradeSwitch;

    /**
     * 切量
     */
    @DCCValue("cutRange:100")
    private String cutRange;

    public boolean isDowngradeSwitch() {
        return "1".equals(downgradeSwitch);
    }

    public boolean isCutRange(String userId) {
        // 哈希值会有负数，取绝对值
        int hashCode = Math.abs(userId.hashCode());

        // 取最后两位
        int lastTwoDigits = hashCode % 100;

        // 判断最后两位是否在范围之内
        if (lastTwoDigits <= Integer.parseInt(cutRange)) {
            return true;
        }
        return false;
    }
}