package com.yimo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.ClaySculpture;
import com.yimo.exception.BusinessException;
import com.yimo.mapper.ClaySculptureMapper;
import com.yimo.service.ClaySculptureService;
import com.yimo.utils.MetaGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 泥塑作品服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClaySculptureServiceImpl implements ClaySculptureService {

    private final ClaySculptureMapper claySculptureMapper;

    @Override
    public Result<ClaySculpture> getById(Long id) {
        ClaySculpture sculpture = claySculptureMapper.selectById(id);
        if (sculpture == null) {
            throw new BusinessException(404, "泥塑作品不存在");
        }
        return Result.success(sculpture);
    }

    @Override
    public Result<PageResult<ClaySculpture>> list(PageRequest request) {
        Page<ClaySculpture> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<ClaySculpture> wrapper = new LambdaQueryWrapper<>();

        // Filter by zodiacId (passed as categoryId in request)
        if (request.getCategoryId() != null) {
            wrapper.eq(ClaySculpture::getZodiacId, request.getCategoryId());
        }

        // Keyword search on name or description
        if (StrUtil.isNotBlank(request.getKeyword())) {
            wrapper.and(w -> w.like(ClaySculpture::getName, request.getKeyword())
                    .or()
                    .like(ClaySculpture::getDescription, request.getKeyword()));
        }

        // Status filter
        if (request.getStatus() != null) {
            wrapper.eq(ClaySculpture::getStatus, request.getStatus());
        }

        wrapper.orderByAsc(ClaySculpture::getSortOrder);

        Page<ClaySculpture> result = claySculptureMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<PageResult<ClaySculpture>> listByZodiac(Long zodiacId, PageRequest request) {
        Page<ClaySculpture> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<ClaySculpture> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClaySculpture::getZodiacId, zodiacId)
                .eq(ClaySculpture::getStatus, 1)
                .orderByAsc(ClaySculpture::getSortOrder);

        Page<ClaySculpture> result = claySculptureMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<PageResult<ClaySculpture>> listByCraftType(String craftType, PageRequest request) {
        Page<ClaySculpture> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<ClaySculpture> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClaySculpture::getCraftType, craftType)
                .eq(ClaySculpture::getStatus, 1)
                .orderByAsc(ClaySculpture::getSortOrder);

        Page<ClaySculpture> result = claySculptureMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<ClaySculpture> create(ClaySculpture sculpture) {
        claySculptureMapper.insert(sculpture);
        log.info("创建泥塑作品成功: id={}, name={}", sculpture.getId(), sculpture.getName());
        return Result.success(sculpture);
    }

    @Override
    public Result<ClaySculpture> update(ClaySculpture sculpture) {
        ClaySculpture existing = claySculptureMapper.selectById(sculpture.getId());
        if (existing == null) {
            throw new BusinessException(404, "泥塑作品不存在");
        }
        claySculptureMapper.updateById(sculpture);
        log.info("更新泥塑作品成功: id={}", sculpture.getId());
        return Result.success(sculpture);
    }

    @Override
    public Result<Void> delete(Long id) {
        ClaySculpture existing = claySculptureMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(404, "泥塑作品不存在");
        }
        claySculptureMapper.deleteById(id);
        log.info("删除泥塑作品成功: id={}", id);
        return Result.success();
    }

    @Override
    public Result<Void> updateStatus(Long id, Integer status) {
        ClaySculpture sculpture = claySculptureMapper.selectById(id);
        if (sculpture == null) {
            throw new BusinessException(404, "泥塑作品不存在");
        }
        sculpture.setStatus(status);
        claySculptureMapper.updateById(sculpture);
        log.info("更新泥塑作品状态成功: id={}, status={}", id, status);
        return Result.success();
    }
}
