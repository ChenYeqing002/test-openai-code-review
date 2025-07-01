package cn.cyq.domain.activity.service.discount;

import cn.cyq.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import java.math.BigDecimal;

public interface IDiscountCalculateService {

    /**
     * 计算折扣
     * @param userId
     * @param originalPrice
     * @param groupBuyDiscount
     * @return
     */
    BigDecimal calculate(String userId, BigDecimal originalPrice,
             GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);
}
