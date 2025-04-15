package com.fkhr.thingsorganizer.apigateway.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DocumentationConfig {
    @Bean
    OpenAPI openAPI(){
        OpenAPI openAPI = new OpenAPI();
        openAPI.info(new Info()
                .title("Tutorial ThingsOrganizer API Gateway Module")
                .description(
                        "ThingsOrganizer project API Gateway module documentation")
                .version("1.0.0")
                .contact(new Contact().name("Fakhriyeh").
                        url("https://fakhriyeh.com").email("test@fakhriyeh.com")));

        return openAPI;
    }

    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }
}
