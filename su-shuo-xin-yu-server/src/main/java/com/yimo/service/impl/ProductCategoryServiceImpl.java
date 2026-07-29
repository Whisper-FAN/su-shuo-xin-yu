package com.yimo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.ProductCategory;
import com.yimo.exception.BusinessException;
import com.yimo.mapper.ProductCategoryMapper;
import com.yimo.service.ProductCategoryService;
import com.yimo.utils.MetaGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品分类服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryMapper productCategoryMapper;

    @Override
    public Result<ProductCategory> getById(Long id) {
        ProductCategory category = productCategoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException(404, "产品分类不存在");
        }
        return Result.success(category);
    }

    @Override
    public Result<PageResult<ProductCategory>> list(PageRequest request) {
        Page<ProductCategory> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<ProductCategory> wrapper = new LambdaQueryWrapper<>();

        if (StrUtil.isNotBlank(request.getKeyword())) {
            wrapper.like(ProductCategory::getName, request.getKeyword());
        }
        if (request.getStatus() != null) {
            wrapper.eq(ProductCategory::getStatus, request.getStatus());
        }
        wrapper.orderByAsc(ProductCategory::getSortOrder);

        Page<ProductCategory> result = productCategoryMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<List<ProductCategory>> listEnabled() {
        List<ProductCategory> categories = productCategoryMapper.selectList(
                new LambdaQueryWrapper<ProductCategory>()
                        .eq(ProductCategory::getStatus, 1)
                        .orderByAsc(ProductCategory::getSortOrder));
        return Result.success(categories);
    }

    @Override
    public Result<ProductCategory> create(ProductCategory category) {
        productCategoryMapper.insert(category);
        log.info("创建产品分类成功: id={}, name={}", category.getId(), category.getName());
        return Result.success(category);
    }

    @Override
    public Result<ProductCategory> update(ProductCategory category) {
        ProductCategory existing = productCategoryMapper.selectById(category.getId());
        if (existing == null) {
            throw new BusinessException(404, "产品分类不存在");
        }
        productCategoryMapper.updateById(category);
        log.info("更新产品分类成功: id={}", category.getId());
        return Result.success(category);
    }

    @Override
    public Result<Void> delete(Long id) {
        ProductCategory existing = productCategoryMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(404, "产品分类不存在");
        }
        productCategoryMapper.deleteById(id);
        log.info("删除产品分类成功: id={}", id);
        return Result.success();
    }

    @Override
    public Result<Void> updateStatus(Long id, Integer status) {
        ProductCategory category = productCategoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException(404, "产品分类不存在");
        }
        category.setStatus(status);
        productCategoryMapper.updateById(category);
        log.info("更新产品分类状态成功: id={}, status={}", id, status);
        return Result.success();
    }
}
