package com.yimo.service;

import com.yimo.common.Result;
import com.yimo.entity.User;

import java.util.Map;

/**
 * 认证服务接口 - 处理登录、登出、注册、令牌刷新等认证相关操作
 *
 * @author yimo-team
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 包含token、用户ID、昵称、头像、角色等信息的响应
     */
    Result<Map<String, Object>> login(String username, String password);

    /**
     * 用户登出
     *
     * @param token 当前登录令牌
     * @return 操作结果
     */
    Result<Void> logout(String token);

    /**
     * 用户注册
     *
     * @param user 用户实体（包含昵称、密码、手机号等注册信息）
     * @return 注册成功的用户信息
     */
    Result<User> register(User user);

    /**
     * 刷新访问令牌
     *
     * @param token 当前令牌
     * @return 包含新token、用户ID、昵称、头像、角色等信息的响应
     */
    Result<Map<String, Object>> refreshToken(String token);

    /**
     * 获取当前登录用户信息
     *
     * @param token 当前登录令牌
     * @return 包含用户ID、昵称、头像、角色、手机号等信息的响应
     */
    Result<Map<String, Object>> currentUser(String token);
}
