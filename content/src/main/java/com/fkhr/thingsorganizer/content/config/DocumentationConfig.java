package com.fkhr.thingsorganizer.content.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DocumentationConfig {
    @Value("${openapi.server.gateway.url}")
    private String serverGatewayUrl;

    @Value("${openapi.server.local.url}")
    private String serverLocalUrl;
    @Bean
    OpenAPI openAPI(){
        Server localServer = new Server();
        localServer.setDescription("gateway");
        localServer.setUrl(serverGatewayUrl);
        Server testServer = new Server();
        testServer.setDescription("local");
        testServer.setUrl(serverLocalUrl);
        OpenAPI openAPI = new OpenAPI();
        openAPI.info(new Info()
                .title("Tutorial ThingsOrganizer Content Module Rest API")
                .description(
                        "ThingsOrganizer project Content module documentation")
                .version("1.0.0")
                .contact(new Contact().name("Fakhriyeh").
                        url("https://fakhriyeh.com").email("test@fakhriyeh.com")));
        openAPI.setServers(Arrays.asList(localServer, testServer));
        openAPI.components(new Components()
                        .addSecuritySchemes("BasicAuthentication",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")))
                // Setting global security requirement
                .security(List.of(new SecurityRequirement().addList("BasicAuthentication")));
        return openAPI;
    }
}
