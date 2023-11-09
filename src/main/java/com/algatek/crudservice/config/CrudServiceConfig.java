package com.algatek.crudservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CrudServiceConfig {
    @ConditionalOnMissingBean
    @Bean(value = "model-mapper")
    @Scope(value = "singleton")
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
