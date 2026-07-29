package com.yimo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Knife4j (Swagger) 配置
 *
 * @author yimo-team
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("塑说心语 API 文档")
                        .description("大吴泥塑文化传承平台后端接口文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("塑说心语团队")
                                .email("3087243250@qq.com"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8088").description("本地开发环境")
                ));
    }
}
