package com.yimo.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yimo.common.Result;
import com.yimo.config.AdminConfig;
import com.yimo.entity.User;
import com.yimo.enums.UserRole;
import com.yimo.exception.BusinessException;
import com.yimo.mapper.UserMapper;
import com.yimo.security.JwtUtils;
import com.yimo.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 认证服务实现
 *
 * @author yimo-team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final AdminConfig adminConfig;

    @Override
    public Result<Map<String, Object>> login(String username, String password) {
        // Admin login
        if (adminConfig.getUsername().equals(username) && adminConfig.getPassword().equals(password)) {
            User admin = userMapper.selectOne(new LambdaQueryWrapper<User>()
                    .eq(User::getRole, UserRole.ADMIN.getCode()));
            if (admin == null) {
                admin = new User();
                admin.setNickname("管理员");
                admin.setRole(UserRole.ADMIN.getCode());
                admin.setPassword(BCrypt.hashpw(adminConfig.getPassword()));
                admin.setLastLoginAt(LocalDateTime.now());
                userMapper.insert(admin);
            } else {
                admin.setLastLoginAt(LocalDateTime.now());
                userMapper.updateById(admin);
            }

            String token = jwtUtils.generateToken(admin.getId(), UserRole.ADMIN.getCode());
            return Result.success(Map.of(
                    "token", token,
                    "userId", admin.getId(),
                    "nickname", admin.getNickname(),
                    "avatar", admin.getAvatar() != null ? admin.getAvatar() : "",
                    "role", UserRole.ADMIN.getCode()
            ));
        }

        throw new BusinessException(401, "用户名或密码错误");
    }

    @Override
    public Result<Map<String, Object>> refreshToken(String token) {
        if (!jwtUtils.validateToken(token)) {
            throw new BusinessException(401, "Token无效或已过期");
        }

        Long userId = jwtUtils.getUserId(token);
        String role = jwtUtils.getRole(token);
        String newToken = jwtUtils.generateToken(userId, role);

        User user = userMapper.selectById(userId);
        return Result.success(Map.of(
                "token", newToken,
                "userId", userId,
                "nickname", user != null ? user.getNickname() : "",
                "avatar", user != null && user.getAvatar() != null ? user.getAvatar() : "",
                "role", role
        ));
    }

    @Override
    public Result<Map<String, Object>> currentUser(String token) {
        Long userId = jwtUtils.getUserId(token);
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(401, "用户不存在");
        }
        return Result.success(Map.of(
                "userId", user.getId(),
                "nickname", user.getNickname(),
                "avatar", user.getAvatar() != null ? user.getAvatar() : "",
                "role", user.getRole(),
                "phone", user.getPhone() != null ? user.getPhone() : ""
        ));
    }

    @Override
    public Result<Void> logout(String token) {
        // Stateless JWT - client-side logout is sufficient
        // Could implement token blacklist with Redis if needed
        return Result.success();
    }

    @Override
    public Result<User> register(User user) {
        // Check if phone already exists
        if (StrUtil.isNotBlank(user.getPhone())) {
            Long count = userMapper.selectCount(new LambdaQueryWrapper<User>()
                    .eq(User::getPhone, user.getPhone()));
            if (count > 0) {
                throw new BusinessException(400, "该手机号已注册");
            }
        }
        user.setRole(UserRole.VISITOR.getCode());
        if (StrUtil.isNotBlank(user.getPassword())) {
            user.setPassword(BCrypt.hashpw(user.getPassword()));
        }
        userMapper.insert(user);
        user.setPassword(null); // Don't return password
        return Result.success(user);
    }
}
