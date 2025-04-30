package com.beyond.homs.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(@Value("${springdoc.swagger-ui.version}") String springdocVersion) {
        Info info = new Info()
                .title("HOMS")
                .version(springdocVersion)
                .description("테스트 가보자ㅏㅏㅏㅏㅏ<br>"
                        + "개발드가자ㅏㅏㅏㅏㅏ<br>"
                        + "인증된 사용자만 접근할 수 있으며, API 요청에 대한 응답으로 JSON 형식의 데이터를 반환합니다.<br>"
                        + "각 API는 Swagger UI를 통해 테스트할 수 있습니다.");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes(
                        "bearer-auth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))
                .addSecurityItem(
                        new SecurityRequirement().addList("bearer-auth")
                )
                .info(info);
    }
}
