package com.yimo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yimo.common.Result;
import com.yimo.entity.ZodiacStory;
import com.yimo.exception.BusinessException;
import com.yimo.mapper.ZodiacStoryMapper;
import com.yimo.service.ZodiacStoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 生肖故事服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ZodiacStoryServiceImpl implements ZodiacStoryService {

    private final ZodiacStoryMapper zodiacStoryMapper;

    @Override
    public Result<ZodiacStory> getById(Long id) {
        ZodiacStory story = zodiacStoryMapper.selectById(id);
        if (story == null) {
            throw new BusinessException(404, "生肖故事不存在");
        }
        return Result.success(story);
    }

    @Override
    public Result<List<ZodiacStory>> listByZodiacId(Long zodiacId) {
        List<ZodiacStory> stories = zodiacStoryMapper.selectList(
                new LambdaQueryWrapper<ZodiacStory>()
                        .eq(ZodiacStory::getZodiacId, zodiacId)
                        .orderByAsc(ZodiacStory::getSortOrder));
        return Result.success(stories);
    }

    @Override
    public Result<ZodiacStory> create(ZodiacStory story) {
        zodiacStoryMapper.insert(story);
        log.info("创建生肖故事成功: id={}, title={}, zodiacId={}", story.getId(), story.getTitle(), story.getZodiacId());
        return Result.success(story);
    }

    @Override
    public Result<ZodiacStory> update(ZodiacStory story) {
        ZodiacStory existing = zodiacStoryMapper.selectById(story.getId());
        if (existing == null) {
            throw new BusinessException(404, "生肖故事不存在");
        }
        zodiacStoryMapper.updateById(story);
        log.info("更新生肖故事成功: id={}", story.getId());
        return Result.success(story);
    }

    @Override
    public Result<Void> delete(Long id) {
        ZodiacStory existing = zodiacStoryMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(404, "生肖故事不存在");
        }
        zodiacStoryMapper.deleteById(id);
        log.info("删除生肖故事成功: id={}", id);
        return Result.success();
    }
}
