package com.tinuvile.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tinuvile
 * @description 应用配置类
 * @since 2025/12/4
 */
@Configuration
@EnableConfigurationProperties(AppConfigProperties.class)
public class AppConfig {
    
    // 这个类的主要目的是启用 AppConfigProperties 配置绑定
    // Spring Boot 会自动将 app.config.* 属性绑定到 AppConfigProperties 实例上
    
}
