package com.yimo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.Gallery;
import com.yimo.exception.BusinessException;
import com.yimo.mapper.GalleryMapper;
import com.yimo.service.GalleryService;
import com.yimo.utils.MetaGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 图库服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryMapper galleryMapper;

    @Override
    public Result<Gallery> getById(Long id) {
        Gallery gallery = galleryMapper.selectById(id);
        if (gallery == null) {
            throw new BusinessException(404, "图库图片不存在");
        }
        return Result.success(gallery);
    }

    @Override
    public Result<PageResult<Gallery>> list(PageRequest request) {
        Page<Gallery> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<Gallery> wrapper = new LambdaQueryWrapper<>();

        if (StrUtil.isNotBlank(request.getKeyword())) {
            wrapper.like(Gallery::getTitle, request.getKeyword());
        }
        if (request.getStatus() != null) {
            wrapper.eq(Gallery::getStatus, request.getStatus());
        }
        wrapper.orderByAsc(Gallery::getSortOrder);

        Page<Gallery> result = galleryMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<PageResult<Gallery>> listByCategory(String category, PageRequest request) {
        Page<Gallery> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<Gallery> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Gallery::getCategory, category)
                .eq(Gallery::getStatus, 1)
                .orderByAsc(Gallery::getSortOrder);

        Page<Gallery> result = galleryMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<Gallery> create(Gallery gallery) {
        galleryMapper.insert(gallery);
        log.info("创建图库图片成功: id={}, title={}", gallery.getId(), gallery.getTitle());
        return Result.success(gallery);
    }

    @Override
    public Result<Gallery> update(Gallery gallery) {
        Gallery existing = galleryMapper.selectById(gallery.getId());
        if (existing == null) {
            throw new BusinessException(404, "图库图片不存在");
        }
        galleryMapper.updateById(gallery);
        log.info("更新图库图片成功: id={}", gallery.getId());
        return Result.success(gallery);
    }

    @Override
    public Result<Void> delete(Long id) {
        Gallery existing = galleryMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(404, "图库图片不存在");
        }
        galleryMapper.deleteById(id);
        log.info("删除图库图片成功: id={}", id);
        return Result.success();
    }

    @Override
    public Result<Void> updateStatus(Long id, Integer status) {
        Gallery gallery = galleryMapper.selectById(id);
        if (gallery == null) {
            throw new BusinessException(404, "图库图片不存在");
        }
        gallery.setStatus(status);
        galleryMapper.updateById(gallery);
        log.info("更新图库图片状态成功: id={}, status={}", id, status);
        return Result.success();
    }
}
