package com.yimo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yimo.common.Result;
import com.yimo.entity.Zodiac;
import com.yimo.entity.ZodiacStory;
import com.yimo.exception.BusinessException;
import com.yimo.mapper.ZodiacMapper;
import com.yimo.mapper.ZodiacStoryMapper;
import com.yimo.service.ZodiacService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 生肖服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ZodiacServiceImpl implements ZodiacService {

    private final ZodiacMapper zodiacMapper;
    private final ZodiacStoryMapper zodiacStoryMapper;

    @Override
    public Result<Zodiac> getById(Long id) {
        Zodiac zodiac = zodiacMapper.selectById(id);
        if (zodiac == null) {
            throw new BusinessException(404, "生肖不存在");
        }
        return Result.success(zodiac);
    }

    @Override
    public Result<List<Zodiac>> listAll() {
        List<Zodiac> zodiacs = zodiacMapper.selectList(
                new LambdaQueryWrapper<Zodiac>()
                        .eq(Zodiac::getStatus, 1)
                        .orderByAsc(Zodiac::getSortOrder));
        return Result.success(zodiacs);
    }

    @Override
    public Result<Map<String, Object>> getWithStories(Long id) {
        Zodiac zodiac = zodiacMapper.selectById(id);
        if (zodiac == null) {
            throw new BusinessException(404, "生肖不存在");
        }

        // Increment view count
        zodiac.setViewCount(zodiac.getViewCount() != null ? zodiac.getViewCount() + 1 : 1L);
        zodiacMapper.updateById(zodiac);

        // Get associated stories
        List<ZodiacStory> stories = zodiacStoryMapper.selectList(
                new LambdaQueryWrapper<ZodiacStory>()
                        .eq(ZodiacStory::getZodiacId, id)
                        .orderByAsc(ZodiacStory::getSortOrder));

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("id", zodiac.getId());
        result.put("name", zodiac.getName());
        result.put("alias", zodiac.getAlias());
        result.put("imageUrl", zodiac.getImageUrl());
        result.put("description", zodiac.getDescription());
        result.put("personality", zodiac.getPersonality());
        result.put("luckyColor", zodiac.getLuckyColor());
        result.put("luckyNumber", zodiac.getLuckyNumber());
        result.put("element", zodiac.getElement());
        result.put("sortOrder", zodiac.getSortOrder());
        result.put("viewCount", zodiac.getViewCount());

        result.put("stories", stories.stream().map(s -> {
            Map<String, Object> storyMap = new LinkedHashMap<>();
            storyMap.put("id", s.getId());
            storyMap.put("title", s.getTitle());
            storyMap.put("content", s.getContent());
            storyMap.put("imageUrl", s.getImageUrl());
            storyMap.put("videoUrl", s.getVideoUrl());
            storyMap.put("sortOrder", s.getSortOrder());
            return storyMap;
        }).collect(Collectors.toList()));

        return Result.success(result);
    }

    @Override
    public Result<Zodiac> create(Zodiac zodiac) {
        if (zodiac.getViewCount() == null) {
            zodiac.setViewCount(0L);
        }
        zodiacMapper.insert(zodiac);
        log.info("创建生肖成功: id={}, name={}", zodiac.getId(), zodiac.getName());
        return Result.success(zodiac);
    }

    @Override
    public Result<Zodiac> update(Zodiac zodiac) {
        Zodiac existing = zodiacMapper.selectById(zodiac.getId());
        if (existing == null) {
            throw new BusinessException(404, "生肖不存在");
        }
        zodiacMapper.updateById(zodiac);
        log.info("更新生肖成功: id={}", zodiac.getId());
        return Result.success(zodiac);
    }

    @Override
    public Result<Void> delete(Long id) {
        Zodiac existing = zodiacMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(404, "生肖不存在");
        }
        zodiacMapper.deleteById(id);
        log.info("删除生肖成功: id={}", id);
        return Result.success();
    }

    @Override
    public Result<Void> incrementViewCount(Long id) {
        Zodiac zodiac = zodiacMapper.selectById(id);
        if (zodiac == null) {
            throw new BusinessException(404, "生肖不存在");
        }
        zodiac.setViewCount(zodiac.getViewCount() != null ? zodiac.getViewCount() + 1 : 1L);
        zodiacMapper.updateById(zodiac);
        return Result.success();
    }

    @Override
    public Result<Void> updateStatus(Long id, Integer status) {
        Zodiac zodiac = zodiacMapper.selectById(id);
        if (zodiac == null) {
            throw new BusinessException(404, "生肖不存在");
        }
        zodiac.setStatus(status);
        zodiacMapper.updateById(zodiac);
        log.info("更新生肖状态成功: id={}, status={}", id, status);
        return Result.success();
    }
}
