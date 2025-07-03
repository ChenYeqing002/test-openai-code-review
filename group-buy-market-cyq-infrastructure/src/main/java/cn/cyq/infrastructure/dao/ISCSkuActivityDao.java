package cn.cyq.infrastructure.dao;

import cn.cyq.infrastructure.dao.po.SCSkuActivity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface ISCSkuActivityDao {

    SCSkuActivity querySkuActivity(SCSkuActivity skuActivity);
}
