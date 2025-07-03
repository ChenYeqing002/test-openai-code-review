package cn.cyq.infrastructure.dao;

import cn.cyq.infrastructure.dao.po.GroupBuyActivity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 拼团活动Dao
 */
@Mapper
public interface IGroupBuyActivityDao {

    List<GroupBuyActivity> queryGroupBuyActivityList();

    /**
     * 查询有效的活动配置
     * @param groupBuyActivity
     * @return
     */
    GroupBuyActivity queryValidGroupBuyActivity(GroupBuyActivity groupBuyActivity);

    GroupBuyActivity queryValidGroupBuyActivityId(Long activityId);
}
