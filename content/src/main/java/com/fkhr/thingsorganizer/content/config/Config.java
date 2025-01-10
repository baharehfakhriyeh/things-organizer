package com.fkhr.thingsorganizer.content.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({com.fkhr.thingsorganizer.common.config.Config.class,
        DocumentationConfig.class})
@ComponentScan(basePackages = {"com.fkhr.thingsorganizer.common.exeptionhandling"})
public class Config {

}
