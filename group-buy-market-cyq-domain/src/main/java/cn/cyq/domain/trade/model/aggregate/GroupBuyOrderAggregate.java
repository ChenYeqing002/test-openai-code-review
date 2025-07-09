package cn.cyq.domain.trade.model.aggregate;

import cn.cyq.domain.trade.model.eneity.PayActivityEntity;
import cn.cyq.domain.trade.model.eneity.PayDiscountEntity;
import cn.cyq.domain.trade.model.eneity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupBuyOrderAggregate {

    private UserEntity userEntity;

    private PayActivityEntity payActivityEntity;

    private PayDiscountEntity payDiscountEntity;
}
