package cn.cyq.infrastructure.adapter.repository;

import cn.cyq.domain.tag.adapter.repository.ITagRepository;
import cn.cyq.domain.tag.model.entity.CrowdTagsJobEntity;
import cn.cyq.infrastructure.dao.ICrowdTagsDao;
import cn.cyq.infrastructure.dao.ICrowdTagsDetailDao;
import cn.cyq.infrastructure.dao.ICrowdTagsJobDao;
import cn.cyq.infrastructure.dao.po.CrowdTags;
import cn.cyq.infrastructure.dao.po.CrowdTagsDetail;
import cn.cyq.infrastructure.dao.po.CrowdTagsJob;
import cn.cyq.infrastructure.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBitSet;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Slf4j
@Repository
public class TagRepository implements ITagRepository {

    @Resource
    private ICrowdTagsDao crowdTagsDao;

    @Resource
    private ICrowdTagsDetailDao crowdTagsDetailDao;

    @Resource
    private ICrowdTagsJobDao crowdTagsJobDao;

    @Resource
    private IRedisService redisService;

    @Override
    public CrowdTagsJobEntity queryCrowdTagsJobEntity(String tagId, String batchId) {

        CrowdTagsJob crowdTagsJobReq = new CrowdTagsJob();
        crowdTagsJobReq.setTagId(tagId);
        crowdTagsJobReq.setBatchId(batchId);

        CrowdTagsJob crowdTagsJob = crowdTagsJobDao.queryCrowdTagsJob(crowdTagsJobReq);
        return CrowdTagsJobEntity.builder()
                .tagType(crowdTagsJob.getTagType())
                .tagRule(crowdTagsJob.getTagRule())
                .statStartTime(crowdTagsJob.getStatStartTime())
                .statEndTime(crowdTagsJob.getStatEndTime())
                .build();
    }

    @Override
    public void addCrowdTagsUserId(String tagId, String userId) {
        CrowdTagsDetail crowdTagsDetailReq = new CrowdTagsDetail();
        crowdTagsDetailReq.setTagId(tagId);
        crowdTagsDetailReq.setUserId(userId);

        try {
            crowdTagsDetailDao.addCrowdTagsUserId(crowdTagsDetailReq);

            RBitSet bitSet = redisService.getBitSet(tagId);
//            bitSet.set(redisService.getIndexFromUserId2(userId));
            bitSet.set(123);
        } catch (DuplicateKeyException ignore) {
        }
    }

    @Override
    public void updateCrowdTagsStatistics(String tagId, int size) {
        CrowdTags crowdTagsReq = new CrowdTags();
        crowdTagsReq.setTagId(tagId);
        crowdTagsReq.setStatistics(size);
        crowdTagsDao.updateCrowdTagsStatistics(crowdTagsReq);
    }
}
