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

}
