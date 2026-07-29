package com.yimo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.Banner;
import com.yimo.exception.BusinessException;
import com.yimo.mapper.BannerMapper;
import com.yimo.service.BannerService;
import com.yimo.utils.MetaGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播图服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerMapper bannerMapper;

    @Override
    public Result<Banner> getById(Long id) {
        Banner banner = bannerMapper.selectById(id);
        if (banner == null) {
            throw new BusinessException(404, "轮播图不存在");
        }
        return Result.success(banner);
    }

    @Override
    public Result<PageResult<Banner>> list(PageRequest request) {
        Page<Banner> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<Banner> wrapper = new LambdaQueryWrapper<>();

        if (request.getStatus() != null) {
            wrapper.eq(Banner::getStatus, request.getStatus());
        }
        wrapper.orderByAsc(Banner::getSortOrder);

        Page<Banner> result = bannerMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<List<Banner>> listEnabled() {
        List<Banner> banners = bannerMapper.selectList(
                new LambdaQueryWrapper<Banner>()
                        .eq(Banner::getStatus, 1)
                        .orderByAsc(Banner::getSortOrder));
        return Result.success(banners);
    }

    @Override
    public Result<Banner> create(Banner banner) {
        bannerMapper.insert(banner);
        log.info("创建轮播图成功: id={}, title={}", banner.getId(), banner.getTitle());
        return Result.success(banner);
    }

    @Override
    public Result<Banner> update(Banner banner) {
        Banner existing = bannerMapper.selectById(banner.getId());
        if (existing == null) {
            throw new BusinessException(404, "轮播图不存在");
        }
        bannerMapper.updateById(banner);
        log.info("更新轮播图成功: id={}", banner.getId());
        return Result.success(banner);
    }

    @Override
    public Result<Void> delete(Long id) {
        Banner existing = bannerMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(404, "轮播图不存在");
        }
        bannerMapper.deleteById(id);
        log.info("删除轮播图成功: id={}", id);
        return Result.success();
    }

    @Override
    public Result<Void> updateStatus(Long id, Integer status) {
        Banner banner = bannerMapper.selectById(id);
        if (banner == null) {
            throw new BusinessException(404, "轮播图不存在");
        }
        banner.setStatus(status);
        bannerMapper.updateById(banner);
        log.info("更新轮播图状态成功: id={}, status={}", id, status);
        return Result.success();
    }
}
