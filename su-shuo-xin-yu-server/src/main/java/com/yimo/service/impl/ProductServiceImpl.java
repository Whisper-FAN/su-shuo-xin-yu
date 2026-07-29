package com.yimo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.Product;
import com.yimo.exception.BusinessException;
import com.yimo.mapper.ProductMapper;
import com.yimo.service.ProductService;
import com.yimo.utils.MetaGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    @Override
    public Result<Product> getById(Long id) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            throw new BusinessException(404, "产品不存在");
        }
        return Result.success(product);
    }

    @Override
    public Result<PageResult<Product>> list(PageRequest request) {
        Page<Product> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();

        // Category filter
        if (request.getCategoryId() != null) {
            wrapper.eq(Product::getCategoryId, request.getCategoryId());
        }

        // Keyword search on name or tags
        if (StrUtil.isNotBlank(request.getKeyword())) {
            wrapper.and(w -> w.like(Product::getName, request.getKeyword())
                    .or()
                    .like(Product::getTags, request.getKeyword()));
        }

        // Status filter
        if (request.getStatus() != null) {
            wrapper.eq(Product::getStatus, request.getStatus());
        }

        // Sort
        if (StrUtil.isNotBlank(request.getSortField())) {
            boolean isAsc = "asc".equalsIgnoreCase(request.getSortOrder());
            String sortField = request.getSortField();
            wrapper.orderBy(true, isAsc, p -> {
                switch (sortField) {
                    case "price":
                        return p.getPrice();
                    case "sales":
                        return p.getSales();
                    case "viewCount":
                        return p.getViewCount();
                    default:
                        return p.getSortOrder();
                }
            });
        } else {
            wrapper.orderByAsc(Product::getSortOrder);
        }

        Page<Product> result = productMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<List<Product>> listHot(Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 8;
        }
        List<Product> products = productMapper.selectList(
                new LambdaQueryWrapper<Product>()
                        .eq(Product::getIsHot, 1)
                        .eq(Product::getStatus, 1)
                        .orderByDesc(Product::getSales)
                        .last("LIMIT " + limit));
        return Result.success(products);
    }

    @Override
    public Result<List<Product>> listRecommended(Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 8;
        }
        List<Product> products = productMapper.selectList(
                new LambdaQueryWrapper<Product>()
                        .eq(Product::getIsRecommend, 1)
                        .eq(Product::getStatus, 1)
                        .orderByAsc(Product::getSortOrder)
                        .last("LIMIT " + limit));
        return Result.success(products);
    }

    @Override
    public Result<PageResult<Product>> listByZodiac(Long zodiacId, PageRequest request) {
        Page<Product> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getZodiacId, zodiacId)
                .eq(Product::getStatus, 1)
                .orderByAsc(Product::getSortOrder);

        Page<Product> result = productMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<Product> create(Product product) {
        if (product.getViewCount() == null) {
            product.setViewCount(0L);
        }
        if (product.getSales() == null) {
            product.setSales(0);
        }
        productMapper.insert(product);
        log.info("创建产品成功: id={}, name={}", product.getId(), product.getName());
        return Result.success(product);
    }

    @Override
    public Result<Product> update(Product product) {
        Product existing = productMapper.selectById(product.getId());
        if (existing == null) {
            throw new BusinessException(404, "产品不存在");
        }
        productMapper.updateById(product);
        log.info("更新产品成功: id={}", product.getId());
        return Result.success(product);
    }

    @Override
    public Result<Void> delete(Long id) {
        Product existing = productMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(404, "产品不存在");
        }
        productMapper.deleteById(id);
        log.info("删除产品成功: id={}", id);
        return Result.success();
    }

    @Override
    public Result<Void> incrementViewCount(Long id) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            throw new BusinessException(404, "产品不存在");
        }
        product.setViewCount(product.getViewCount() != null ? product.getViewCount() + 1 : 1L);
        productMapper.updateById(product);
        return Result.success();
    }

    @Override
    public Result<Void> incrementSales(Long id, Integer quantity) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            throw new BusinessException(404, "产品不存在");
        }
        product.setSales((product.getSales() != null ? product.getSales() : 0) + (quantity != null ? quantity : 1));
        productMapper.updateById(product);
        return Result.success();
    }

    @Override
    public Result<Void> updateStatus(Long id, Integer status) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            throw new BusinessException(404, "产品不存在");
        }
        product.setStatus(status);
        productMapper.updateById(product);
        log.info("更新产品状态成功: id={}, status={}", id, status);
        return Result.success();
    }
}
