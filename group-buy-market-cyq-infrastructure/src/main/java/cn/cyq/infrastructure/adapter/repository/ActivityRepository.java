package cn.cyq.infrastructure.adapter.repository;

import cn.cyq.domain.activity.adapter.repository.IActivityRepository;
import cn.cyq.domain.activity.model.valobj.DiscountTypeEnum;
import cn.cyq.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import cn.cyq.domain.activity.model.valobj.SCSkuActivityVO;
import cn.cyq.domain.activity.model.valobj.SkuVO;
import cn.cyq.infrastructure.dao.IGroupBuyActivityDao;
import cn.cyq.infrastructure.dao.IGroupBuyDiscountDao;
import cn.cyq.infrastructure.dao.ISCSkuActivityDao;
import cn.cyq.infrastructure.dao.ISkuDao;
import cn.cyq.infrastructure.dao.po.GroupBuyActivity;
import cn.cyq.infrastructure.dao.po.GroupBuyDiscount;
import cn.cyq.infrastructure.dao.po.SCSkuActivity;
import cn.cyq.infrastructure.dao.po.Sku;
import cn.cyq.infrastructure.dcc.DCCService;
import cn.cyq.infrastructure.redis.IRedisService;
import org.redisson.api.RBitSet;
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

    @Resource
    private ISCSkuActivityDao skuActivityDao;

    @Resource
    private IRedisService redisService;

    @Resource
    private DCCService dccService;

    @Override
    public GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(Long activityId) {
        // 通过SC渠道值查询配置中最新的1个有效活动
        GroupBuyActivity groupBuyActivity = groupBuyActivityDao.queryValidGroupBuyActivityId(activityId);
        if (null == groupBuyActivity) {
            return null;
        }

        // 通过活动id获取活动配置
        String discountId = groupBuyActivity.getDiscountId();

        GroupBuyDiscount groupBuyDiscountRes = groupBuyDiscountDao.queryGroupBuyActivityDiscountByDiscountId(discountId);
        if (null == groupBuyDiscountRes) {
            return null;
        }
        GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount = GroupBuyActivityDiscountVO.GroupBuyDiscount.builder()
                .discountName(groupBuyDiscountRes.getDiscountName())
                .discountDesc(groupBuyDiscountRes.getDiscountDesc())
                .discountType(DiscountTypeEnum.getByCode(groupBuyDiscountRes.getDiscountType()))
                .marketPlan(groupBuyDiscountRes.getMarketPlan())
                .marketExpr(groupBuyDiscountRes.getMarketExpr())
                .tagId(groupBuyDiscountRes.getTagId())
                .build();

        return GroupBuyActivityDiscountVO.builder()
                .activityId(groupBuyActivity.getActivityId())
                .activityName(groupBuyActivity.getActivityName())
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
        if (null == sku) {
            return null;
        }
        return SkuVO.builder()
                .goodsId(sku.getGoodsId())
                .goodsName(sku.getGoodsName())
                .originalPrice(sku.getOriginalPrice())
                .build();
    }

    @Override
    public SCSkuActivityVO querySCSkuActivityBySCGoodsId(String source, String channel, String goodsId) {
        SCSkuActivity scSkuActivityReq = new SCSkuActivity();
        scSkuActivityReq.setSource(source);
        scSkuActivityReq.setChannel(channel);
        scSkuActivityReq.setGoodsId(goodsId);

        SCSkuActivity scSkuActivity = skuActivityDao.querySkuActivity(scSkuActivityReq);
        if (null == scSkuActivity) {
            return null;
        }
        return SCSkuActivityVO.builder()
                .source(scSkuActivity.getSource())
                .channel(scSkuActivity.getChannel())
                .activityId(scSkuActivity.getActivityId())
                .goodsId(scSkuActivity.getGoodsId())
                .build();
    }

    @Override
    public boolean isTagCrowdRange(String tagId, String userId) {
        RBitSet bitSet = redisService.getBitSet(tagId);
        // 如果不存在，说明这个人群标签失效了，放行所有用户
        if (!bitSet.isExists()) {
            return Boolean.TRUE;
        }
        // 判断用户是否在标签内，在就放行，不在就拦截
        return bitSet.get(redisService.getIndexFromUserId2(userId));
    }

    @Override
    public boolean downgradeSwitch() {
        return dccService.isDowngradeSwitch();
    }

    @Override
    public boolean cutRange(String userId) {
        return dccService.isCutRange(userId);
    }
}
