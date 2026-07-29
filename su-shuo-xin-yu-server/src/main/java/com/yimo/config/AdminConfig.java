package com.yimo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 管理员配置
 *
 * @author yimo-team
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "admin")
public class AdminConfig {
    private String username;
    private String password;
}
