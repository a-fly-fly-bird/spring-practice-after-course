package pers.terry.springpracticeaftercourse.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
  @Bean
  public OpenAPI mallTinyOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("论坛后端").description("SpringDoc API 演示").version("v1.0.0"))
        .components(
            new Components()
                // 设置 spring security jwt accessToken 认证的请求头 Authorization: Bearer xxx.xxx.xxx
                .addSecuritySchemes(
                    "authScheme",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .bearerFormat("JWT")
                        .scheme("bearer")));
  }
}
