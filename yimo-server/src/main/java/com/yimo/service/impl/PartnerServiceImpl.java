package com.yimo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.Partner;
import com.yimo.exception.BusinessException;
import com.yimo.mapper.PartnerMapper;
import com.yimo.service.PartnerService;
import com.yimo.utils.MetaGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 合作伙伴服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerMapper partnerMapper;

    @Override
    public Result<Partner> getById(Long id) {
        Partner partner = partnerMapper.selectById(id);
        if (partner == null) {
            throw new BusinessException(404, "合作伙伴不存在");
        }
        return Result.success(partner);
    }

    @Override
    public Result<PageResult<Partner>> list(PageRequest request) {
        Page<Partner> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<Partner> wrapper = new LambdaQueryWrapper<>();

        if (StrUtil.isNotBlank(request.getKeyword())) {
            wrapper.like(Partner::getName, request.getKeyword());
        }
        if (request.getStatus() != null) {
            wrapper.eq(Partner::getStatus, request.getStatus());
        }
        wrapper.orderByAsc(Partner::getSortOrder);

        Page<Partner> result = partnerMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<List<Partner>> listEnabled() {
        List<Partner> partners = partnerMapper.selectList(
                new LambdaQueryWrapper<Partner>()
                        .eq(Partner::getStatus, 1)
                        .orderByAsc(Partner::getSortOrder));
        return Result.success(partners);
    }

    @Override
    public Result<Partner> create(Partner partner) {
        partnerMapper.insert(partner);
        log.info("创建合作伙伴成功: id={}, name={}", partner.getId(), partner.getName());
        return Result.success(partner);
    }

    @Override
    public Result<Partner> update(Partner partner) {
        Partner existing = partnerMapper.selectById(partner.getId());
        if (existing == null) {
            throw new BusinessException(404, "合作伙伴不存在");
        }
        partnerMapper.updateById(partner);
        log.info("更新合作伙伴成功: id={}", partner.getId());
        return Result.success(partner);
    }

    @Override
    public Result<Void> delete(Long id) {
        Partner existing = partnerMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(404, "合作伙伴不存在");
        }
        partnerMapper.deleteById(id);
        log.info("删除合作伙伴成功: id={}", id);
        return Result.success();
    }

    @Override
    public Result<Void> updateStatus(Long id, Integer status) {
        Partner partner = partnerMapper.selectById(id);
        if (partner == null) {
            throw new BusinessException(404, "合作伙伴不存在");
        }
        partner.setStatus(status);
        partnerMapper.updateById(partner);
        log.info("更新合作伙伴状态成功: id={}, status={}", id, status);
        return Result.success();
    }
}
