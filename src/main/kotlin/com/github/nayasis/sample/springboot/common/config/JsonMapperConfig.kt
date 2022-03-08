package com.github.nayasis.sample.springboot.common.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.nayasis.kotlin.spring.extension.config.mapper.ObjectMapperBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JsonMapperConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapperBuilder().fieldMapper().apply {
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
        }
    }

}