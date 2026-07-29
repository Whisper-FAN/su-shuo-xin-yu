package com.yimo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 塑说心语 - 大吴泥塑文化传承平台
 *
 * @author yimo-team
 */
@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan("com.yimo.config")
public class YimoApplication {

    public static void main(String[] args) {
        SpringApplication.run(YimoApplication.class, args);
    }
}
