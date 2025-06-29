package cn.cyq.domain.activity.service;

import cn.cyq.domain.activity.model.entity.MarketProductEntity;
import cn.cyq.domain.activity.model.entity.TrialBalanceEntity;

public interface IIndexGroupBuyMarketService {

    TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception;
}
