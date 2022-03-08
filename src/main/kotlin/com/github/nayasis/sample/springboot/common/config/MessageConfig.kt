package com.github.nayasis.sample.springboot.common.config

import com.github.nayasis.kotlin.spring.extension.config.message.CustomMessageSource
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MessageConfig {

    @Bean
    fun messageSource(): MessageSource? {
        return CustomMessageSource()
    }

}