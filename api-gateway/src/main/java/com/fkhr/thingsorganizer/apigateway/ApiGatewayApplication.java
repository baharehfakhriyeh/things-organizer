package com.fkhr.thingsorganizer.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
/*//todo: uncomment when added auth to this module.
@ComponentScan(basePackages = {
        "com.fkhr.thingsorganizer.commonsecurity.service",
        "com.fkhr.thingsorganizer.apigateway"})*/
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
