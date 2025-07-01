package cn.cyq.domain.activity.service.discount;

import cn.cyq.domain.activity.model.valobj.DiscountTypeEnum;
import cn.cyq.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import java.math.BigDecimal;

public abstract class AbstractDiscountCalculateService implements IDiscountCalculateService{

    @Override
    public BigDecimal calculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        if (DiscountTypeEnum.TAG.equals(groupBuyDiscount.getDiscountType())) {
            boolean isCrowdRange = filterTagId(userId, groupBuyDiscount.getTagId());
            if (!isCrowdRange) {
                return originalPrice;
            }
        }
        return doCalculate(originalPrice, groupBuyDiscount);
    }

    private boolean filterTagId(String userId, String tagId) {
        return true;
    }

    protected abstract BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);
}
