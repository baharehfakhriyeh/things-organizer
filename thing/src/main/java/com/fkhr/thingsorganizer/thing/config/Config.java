package com.fkhr.thingsorganizer.thing.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableAspectJAutoProxy
//@EnableFeignClients(basePackages = "com.fkhr.thingsorganizer")
@EnableCaching
@EnableDiscoveryClient
@ComponentScan(basePackages = {
        "com.fkhr.thingsorganizer.commonsecurity.service",
        "com.fkhr.thingsorganizer.thing"})
@Import({com.fkhr.thingsorganizer.common.config.Config.class,
        com.fkhr.thingsorganizer.commonsecurity.config.SecurityConfig.class
})
public class Config {

}
