package cn.lzhch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置类
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/22 22:08
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有路径
                .allowedOrigins("*") // 允许的源
                // .allowedOriginPatterns("https://liuzhichao.com.cn") // 使用模式匹配
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"); // 允许的方法
        // .allowCredentials(false); // 允许凭据
    }

}

