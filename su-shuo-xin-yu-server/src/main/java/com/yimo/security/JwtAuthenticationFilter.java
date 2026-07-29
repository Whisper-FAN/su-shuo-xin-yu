package com.yimo.security;

import cn.hutool.core.util.StrUtil;
import com.yimo.common.Result;
import com.yimo.enums.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JWT 认证过滤器
 *
 * @author yimo-team
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;

    private static final List<String> WHITE_LIST = List.of(
            "/api/auth/login",
            "/api/auth/register",
            "/api/user/register",
            "/api/zodiac/list",
            "/api/zodiac/",
            "/api/story/",
            "/api/article/",
            "/api/banner/",
            "/api/gallery/",
            "/api/product/",
            "/api/test/question",
            "/api/test/submit",
            "/api/test/result/",
            "/api/category/",
            "/api/clay-sculpture/",
            "/api/partner/",
            "/api/team-member/",
            "/api/statistics/",
            "/api/file/",
            "/swagger-ui",
            "/v3/api-docs",
            "/doc.html",
            "/uploads",
            "/favicon.ico"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // 白名单放行
        if (isWhiteListed(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // OPTIONS 预检请求放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 获取Token
        String token = extractToken(request);
        if (StrUtil.isBlank(token)) {
            writeUnauthorized(response, "未提供认证Token");
            return;
        }

        // 验证Token
        if (!jwtUtils.validateToken(token)) {
            writeUnauthorized(response, "Token无效或已过期");
            return;
        }

        // 检查过期
        if (jwtUtils.isTokenExpired(token)) {
            writeUnauthorized(response, "Token已过期，请重新登录");
            return;
        }

        // 设置用户信息到请求属性
        Long userId = jwtUtils.getUserId(token);
        String role = jwtUtils.getRole(token);
        request.setAttribute("userId", userId);
        request.setAttribute("role", role);

        filterChain.doFilter(request, response);
    }

    private boolean isWhiteListed(String path) {
        return WHITE_LIST.stream().anyMatch(path::startsWith);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return request.getParameter("token");
    }

    private void writeUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(
                Result.unauthorized(message)));
    }
}
