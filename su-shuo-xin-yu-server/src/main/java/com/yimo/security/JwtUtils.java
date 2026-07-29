package com.yimo.security;

import com.yimo.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 *
 * @author yimo-team
 */
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtConfig jwtConfig;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成JWT Token
     */
    public String generateToken(Long userId, String role, Map<String, Object> extraClaims) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtConfig.getExpiration());

        var builder = Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiration);

        if (extraClaims != null && !extraClaims.isEmpty()) {
            extraClaims.forEach(builder::claim);
        }

        return builder.signWith(getSigningKey()).compact();
    }

    /**
     * 生成JWT Token (不带额外信息)
     */
    public String generateToken(Long userId, String role) {
        return generateToken(userId, role, null);
    }

    /**
     * 从Token中解析Claims
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 从Token中获取用户ID
     */
    public Long getUserId(String token) {
        return Long.parseLong(parseToken(token).getSubject());
    }

    /**
     * 从Token中获取角色
     */
    public String getRole(String token) {
        return parseToken(token).get("role", String.class);
    }

    /**
     * 验证Token是否有效
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 检查Token是否过期
     */
    public boolean isTokenExpired(String token) {
        try {
            return parseToken(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 计算Token剩余有效时间(毫秒)
     */
    public long getRemainingTime(String token) {
        try {
            return parseToken(token).getExpiration().getTime() - System.currentTimeMillis();
        } catch (Exception e) {
            return 0;
        }
    }
}
