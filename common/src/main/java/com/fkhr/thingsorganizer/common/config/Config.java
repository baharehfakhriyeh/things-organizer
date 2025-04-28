package com.fkhr.thingsorganizer.common.config;

import com.fkhr.thingsorganizer.common.logging.LoggingAspect;
import com.fkhr.thingsorganizer.common.util.InfoProperties;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@ComponentScan(basePackages = {"com.fkhr.thingsorganizer.common.exeptionhandling",
                                "com.fkhr.thingsorganizer.common.logging",
                                "com.fkhr.thingsorganizer.common.util"})
@PropertySource("classpath:shared.properties")
@EnableAspectJAutoProxy
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Config {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
