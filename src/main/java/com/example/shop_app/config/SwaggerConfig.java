package com.example.shop_app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI feedAppOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Feed App API")
                        .description("Feed App CRUD 실습 API 문서")
                        .version("v1.0.0"));
    }
}