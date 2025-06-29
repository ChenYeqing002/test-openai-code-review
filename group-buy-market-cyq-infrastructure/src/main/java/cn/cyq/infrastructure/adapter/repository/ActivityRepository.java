package cn.cyq.infrastructure.adapter.repository;

import cn.cyq.domain.activity.adapter.repository.IActivityRepository;
import cn.cyq.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import cn.cyq.domain.activity.model.valobj.SkuVO;
import cn.cyq.infrastructure.dao.IGroupBuyActivityDao;
import cn.cyq.infrastructure.dao.IGroupBuyDiscountDao;
import cn.cyq.infrastructure.dao.ISkuDao;
import cn.cyq.infrastructure.dao.po.GroupBuyActivity;
import cn.cyq.infrastructure.dao.po.GroupBuyDiscount;
import cn.cyq.infrastructure.dao.po.Sku;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IGroupBuyActivityDao groupBuyActivityDao;

    @Resource
    private IGroupBuyDiscountDao groupBuyDiscountDao;

    @Resource
    private ISkuDao skuDao;

    @Override
    public GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(String source, String channel) {
        // 通过SC渠道值查询配置中最新的1个有效活动
        GroupBuyActivity groupBuyActivity = groupBuyActivityDao.queryValidGroupBuyActivity(GroupBuyActivity.builder()
                .source(source)
                .channel(channel)
                .build());

        // 通过活动id获取活动配置
        String discountId = groupBuyActivity.getDiscountId();

        GroupBuyDiscount groupBuyDiscountRes = groupBuyDiscountDao.queryGroupBuyActivityDiscountByDiscountId(discountId);
        GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount = GroupBuyActivityDiscountVO.GroupBuyDiscount.builder()
                .discountName(groupBuyDiscountRes.getDiscountName())
                .discountDesc(groupBuyDiscountRes.getDiscountDesc())
                .discountType(groupBuyDiscountRes.getDiscountType())
                .marketPlan(groupBuyDiscountRes.getMarketPlan())
                .marketExpr(groupBuyDiscountRes.getMarketExpr())
                .tagId(groupBuyDiscountRes.getTagId())
                .build();

        return GroupBuyActivityDiscountVO.builder()
                .activityId(groupBuyActivity.getActivityId())
                .activityName(groupBuyActivity.getActivityName())
                .source(groupBuyActivity.getSource())
                .channel(groupBuyActivity.getChannel())
                .goodsId(groupBuyActivity.getGoodsId())
                .groupType(groupBuyActivity.getGroupType())
                .groupBuyDiscount(groupBuyDiscount)
                .takeLimitCount(groupBuyActivity.getTakeLimitCount())
                .target(groupBuyActivity.getTarget())
                .validTime(groupBuyActivity.getValidTime())
                .status(groupBuyActivity.getStatus())
                .startTime(groupBuyActivity.getStartTime())
                .endTime(groupBuyActivity.getEndTime())
                .tagId(groupBuyActivity.getTagId())
                .tagScope(groupBuyActivity.getTagScope())
                .build();
    }

    @Override
    public SkuVO querySkuByGoodsId(String goodsId) {
        Sku sku = skuDao.querySkuByGoodsId(goodsId);
        return SkuVO.builder()
                .goodsId(sku.getGoodsId())
                .goodsName(sku.getGoodsName())
                .originalPrice(sku.getOriginalPrice())
                .build();
    }
}
