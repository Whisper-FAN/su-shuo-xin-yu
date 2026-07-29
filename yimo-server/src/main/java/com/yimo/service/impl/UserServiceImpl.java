package com.yimo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimo.common.PageRequest;
import com.yimo.common.PageResult;
import com.yimo.common.Result;
import com.yimo.entity.User;
import com.yimo.exception.BusinessException;
import com.yimo.mapper.UserMapper;
import com.yimo.service.UserService;
import com.yimo.utils.MetaGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public Result<User> getById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        return Result.success(user);
    }

    @Override
    public Result<PageResult<User>> list(PageRequest request) {
        Page<User> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        // Keyword search on nickname or phone
        if (StrUtil.isNotBlank(request.getKeyword())) {
            wrapper.and(w -> w.like(User::getNickname, request.getKeyword())
                    .or()
                    .like(User::getPhone, request.getKeyword()));
        }

        // Status filter
        if (request.getStatus() != null) {
            wrapper.eq(User::getRole, request.getStatus());
        }

        // Sort
        if (StrUtil.isNotBlank(request.getSortField())) {
            boolean isAsc = "asc".equalsIgnoreCase(request.getSortOrder());
            wrapper.orderBy(true, isAsc, User::getCreateTime);
        } else {
            wrapper.orderByDesc(User::getCreateTime);
        }

        Page<User> result = userMapper.selectPage(page, wrapper);
        return Result.success(MetaGenerator.fromPage(result));
    }

    @Override
    public Result<User> create(User user) {
        userMapper.insert(user);
        log.info("创建用户成功: id={}, nickname={}", user.getId(), user.getNickname());
        return Result.success(user);
    }

    @Override
    public Result<User> update(User user) {
        User existing = userMapper.selectById(user.getId());
        if (existing == null) {
            throw new BusinessException(404, "用户不存在");
        }
        userMapper.updateById(user);
        log.info("更新用户成功: id={}", user.getId());
        return Result.success(user);
    }

    @Override
    public Result<Void> delete(Long id) {
        User existing = userMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(404, "用户不存在");
        }
        userMapper.deleteById(id);
        log.info("删除用户成功: id={}", id);
        return Result.success();
    }

    @Override
    public Result<Void> deleteBatch(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(400, "请选择要删除的用户");
        }
        userMapper.deleteBatchIds(ids);
        log.info("批量删除用户成功: ids={}", ids);
        return Result.success();
    }

    @Override
    public Result<Void> updateStatus(Long id, Integer status) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        user.setRole(String.valueOf(status));
        userMapper.updateById(user);
        log.info("更新用户状态成功: id={}, status={}", id, status);
        return Result.success();
    }
}
