package com.fkhr.thingsorganizer.thing.config;

import org.springframework.context.annotation.*;

@Configuration
@Import({com.fkhr.thingsorganizer.common.config.Config.class,
        com.fkhr.thingsorganizer.commonsecurity.config.SecurityConfig.class
})
public class Config {

}
