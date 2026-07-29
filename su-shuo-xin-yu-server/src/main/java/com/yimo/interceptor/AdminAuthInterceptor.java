package com.yimo.interceptor;

import com.yimo.common.Result;
import com.yimo.enums.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 管理员权限拦截器
 *
 * @author yimo-team
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AdminAuthInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String role = (String) request.getAttribute("role");

        if (role == null || !UserRole.ADMIN.getCode().equals(role)) {
            log.warn("非管理员访问受限资源: {}", request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(
                    Result.forbidden("需要管理员权限")));
            return false;
        }

        return true;
    }
}
