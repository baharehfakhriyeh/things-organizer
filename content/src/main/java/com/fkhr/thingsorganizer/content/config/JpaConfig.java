package com.fkhr.thingsorganizer.content.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = {
                "com.fkhr.thingsorganizer.content.repository",
                "com.fkhr.thingsorganizer.commonsecurity.repository"
        }
)
@EntityScan(
        basePackages = {
                "com.fkhr.thingsorganizer.content.model",
                "com.fkhr.thingsorganizer.commonsecurity.model"}
)
public class JpaConfig {
}
