package com.fkhr.thingsorganizer.thing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
@EnableFeignClients(basePackages = "com.fkhr.thingsorganizer")
@EnableCaching
@EnableDiscoveryClient
@ComponentScan(basePackages = {
        "com.fkhr.thingsorganizer.commonsecurity.service",
        "com.fkhr.thingsorganizer.thing"})
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class ThingApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThingApplication.class, args);
    }

}
