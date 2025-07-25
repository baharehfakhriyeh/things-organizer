package com.fkhr.thingsorganizer.commonsecurity.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import({com.fkhr.thingsorganizer.common.config.Config.class,
        com.fkhr.thingsorganizer.commonsecurity.config.SecurityConfig.class
})
@EnableJpaRepositories(
        basePackages = "com.fkhr.thingsorganizer.commonsecurity.repository"
)
@EntityScan(
        basePackages = "com.fkhr.thingsorganizer.commonsecurity.model"
)
public class TestConfig {
}
