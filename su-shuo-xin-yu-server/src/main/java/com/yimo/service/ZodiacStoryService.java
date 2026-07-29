package com.yimo.service;

import com.yimo.common.Result;
import com.yimo.entity.ZodiacStory;

import java.util.List;

/**
 * 生肖故事服务接口 - 提供生肖故事的增删改查及按生肖ID查询功能
 *
 * @author yimo-team
 */
public interface ZodiacStoryService {

    /**
     * 根据ID查询生肖故事
     *
     * @param id 故事ID
     * @return 生肖故事实体
     */
    Result<ZodiacStory> getById(Long id);

    /**
     * 根据生肖ID查询关联的故事列表
     *
     * @param zodiacId 生肖ID
     * @return 故事列表
     */
    Result<List<ZodiacStory>> listByZodiacId(Long zodiacId);

    /**
     * 新增生肖故事
     *
     * @param story 生肖故事实体
     * @return 新增后的生肖故事实体
     */
    Result<ZodiacStory> create(ZodiacStory story);

    /**
     * 更新生肖故事
     *
     * @param story 生肖故事实体（需包含ID）
     * @return 更新后的生肖故事实体
     */
    Result<ZodiacStory> update(ZodiacStory story);

    /**
     * 删除生肖故事（逻辑删除）
     *
     * @param id 故事ID
     * @return 操作结果
     */
    Result<Void> delete(Long id);
}
