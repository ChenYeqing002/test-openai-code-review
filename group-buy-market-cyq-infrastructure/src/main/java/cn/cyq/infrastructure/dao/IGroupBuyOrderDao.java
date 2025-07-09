package cn.cyq.infrastructure.dao;

import cn.cyq.infrastructure.dao.po.GroupBuyOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IGroupBuyOrderDao {

    void insert(GroupBuyOrder groupBuyOrder);

    // 下单增加
    int updateAddLockCount(String teamId);

    // 退单减少
    int updateSubtractionLockCount(String teamId);

    GroupBuyOrder queryGroupBuyProgress(String teamId);
}
