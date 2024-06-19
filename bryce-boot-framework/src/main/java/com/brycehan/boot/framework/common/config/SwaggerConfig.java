package com.brycehan.boot.framework.common.config;

import com.brycehan.boot.framework.security.TokenUtils;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger 接口配置
 *
 * @since 2022/5/13
 * @author Bryce Han
 */
@Configuration
public class SwaggerConfig {

    private static final String apiTitle = "Bryce Boot接口文档";
    private static final String apiDescription = "Bryce Boot";
    private static final String apiVersion = "0.0.1";
    private static final String termsOfService = "https://github.com/brycehan";
    private static final String contactName = "Bryce Han";
    private static final String contactEmail = "brycehan@163.com";
    private static final String SECURITY_SCHEME_NAME = "BearerToken";

    @Bean
    public GroupedOpenApi bryceBootApi() {
        String[] paths = { "/**" };
        String[] packagesToScan = { "com.brycehan.boot" };
        return GroupedOpenApi.builder()
                .group("BryceBoot")
                .pathsToMatch(paths)
                .packagesToScan(packagesToScan)
                .build();
    }
    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setName(contactName);
        contact.setEmail(contactEmail);

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(
                        new Components()
                                .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                        new SecurityScheme()
                                                .name(SECURITY_SCHEME_NAME)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info()
                        .title(apiTitle)
                        .description(apiDescription)
                        .contact(contact)
                        .version(apiVersion)
                        .termsOfService(termsOfService)
                );
    }

    @Bean
    public GlobalOpenApiCustomizer globalOpenApiCustomizer() {
        HeaderParameter headerParameter = new HeaderParameter();
        headerParameter.setName(TokenUtils.SOURCE_CLIENT_HEADER);
        headerParameter.setRequired(true);
        headerParameter.setDescription("来源客户端");

        StringSchema stringSchema = new StringSchema();
        stringSchema._enum(List.of("pc", "h5", "app"));
        stringSchema._default("pc");
        headerParameter.setSchema(stringSchema);

        return openApi -> openApi.getPaths().values().stream().flatMap(pathItem -> pathItem.readOperations().stream())
                .forEach(operation -> operation.addParametersItem(headerParameter));
    }

}
