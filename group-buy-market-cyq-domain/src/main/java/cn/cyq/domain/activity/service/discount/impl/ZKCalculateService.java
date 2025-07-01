package cn.cyq.domain.activity.service.discount.impl;

import cn.cyq.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import cn.cyq.domain.activity.service.discount.AbstractDiscountCalculateService;
import cn.cyq.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service("ZK")
public class ZKCalculateService extends AbstractDiscountCalculateService {
    @Override
    protected BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        // 获取配置的公式
        String marketExpr = groupBuyDiscount.getMarketExpr();

        BigDecimal deductionPrice = originalPrice.multiply(new BigDecimal(marketExpr));

        if (deductionPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return new BigDecimal("0.01");
        }
        return deductionPrice;
    }
}
