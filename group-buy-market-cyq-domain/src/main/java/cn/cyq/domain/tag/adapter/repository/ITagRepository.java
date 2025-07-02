package cn.cyq.domain.tag.adapter.repository;

import cn.cyq.domain.tag.model.entity.CrowdTagsJobEntity;

public interface ITagRepository {
    CrowdTagsJobEntity queryCrowdTagsJobEntity(String tagId, String batchId);

    void addCrowdTagsUserId(String tagId, String userId);

    void updateCrowdTagsStatistics(String tagId, int size);
}
