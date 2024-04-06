package pers.terry.springpracticeaftercourse.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
  @Bean
  public OpenAPI mallTinyOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("论坛后端").description("SpringDoc API 演示").version("v1.0.0"));
  }
}
