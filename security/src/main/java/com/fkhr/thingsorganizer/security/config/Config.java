package com.fkhr.thingsorganizer.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({com.fkhr.thingsorganizer.common.config.Config.class,
        com.fkhr.thingsorganizer.commonsecurity.config.SecurityConfig.class
})
public class Config {

}
