package com.github.nayasis.sample.springboot

import com.github.nayasis.kotlin.basica.model.Messages
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.aspectj.EnableSpringConfigured
import org.springframework.core.env.Environment
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.*

private val log = KotlinLogging.logger{}

@EnableFeignClients
@EnableAspectJAutoProxy
@EnableSpringConfigured
@EnableConfigurationProperties
@EnableEncryptableProperties
@EnableAsync
@EnableScheduling
@SpringBootApplication
class BootApplication(environment: Environment) {
    init {
        if( ! environment.activeProfiles.contains("local") ) {
            log.info { ">> set timezone to UTC" }
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        }
    }
}

fun main(vararg args: String) {
    Messages.loadFromResource("message/*.prop")
    runApplication<BootApplication>(*args)
}