package com.rctoyshop.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/api/**")

                // 📢 關鍵修正：確保這裡包含 http://localhost:5173
                // 使用 allowedOriginPatterns("*") 以避免 localhost vs 127.0.0.1 或 port 差異問題
                .allowedOriginPatterns("*")

                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .allowedHeaders("*");
    }
}