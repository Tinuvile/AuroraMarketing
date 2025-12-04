package com.tinuvile.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Tinuvile
 * @description 应用配置属性类
 * @since 2025/12/4
 */
@Data
@ConfigurationProperties(prefix = "app.config", ignoreInvalidFields = true)
public class AppConfigProperties {

    /** API版本 */
    private String apiVersion = "v1";

    /** 跨域配置 */
    private String crossOrigin = "*";

}
