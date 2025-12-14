package com.rctoyshop.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
        // 【修正點】: 這是保持不變的 Web 訪問路徑
        registry.addResourceHandler("/images/**")
                // 【修正點】: 這是實際對應的本地路徑
                // 確保您的圖片檔案直接位於這個目錄下，例如 C:\...\image\new_figure.jpg
                .addResourceLocations("file:///C:/rc_toy_shop/backend/build/resources/image/");
    }
}
// 📢 注意：WebConfig 保持不變，因為我們希望 Web Path 還是 /images/**