package com.fkhr.thingsorganizer.common.config;

import com.fkhr.thingsorganizer.common.logging.LoggingAspect;
import com.fkhr.thingsorganizer.common.util.InfoProperties;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = {"com.fkhr.thingsorganizer.common.exeptionhandling",
                                "com.fkhr.thingsorganizer.common.logging",
                                "com.fkhr.thingsorganizer.common.util"})
@PropertySource("classpath:shared.properties")
@EnableAspectJAutoProxy
public class Config {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
