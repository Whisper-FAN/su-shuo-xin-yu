package com.yimo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * OSS 配置
 *
 * @author yimo-team
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "oss")
public class OssConfig {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucket;
}
