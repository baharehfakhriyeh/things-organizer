package com.fkhr.thingsorganizer.thing.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

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
                .title("Tutorial ThingsOrganizer Thing Module Rest API")
                .description(
                        "ThingsOrganizer project Thing module documentation")
                .version("1.0.0")
                .contact(new Contact().name("Fakhriyeh").
                        url("https://fakhriyeh.com").email("test@fakhriyeh.com")));
        openAPI.setServers(Arrays.asList(localServer, testServer));
        return openAPI;
    }
}
