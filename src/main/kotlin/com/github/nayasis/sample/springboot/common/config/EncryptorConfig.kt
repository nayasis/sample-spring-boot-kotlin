package com.github.nayasis.sample.springboot.common.config

import com.github.nayasis.sample.springboot.common.Constant.Companion.ENCRYPT_PASSWORD
import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

const val ALGORITHM = "PBEWithMD5AndDES"

@Configuration
class EncryptorConfig {

    @Bean("jasyptStringEncryptor")
    fun encryptor(): StringEncryptor {

        val config = SimpleStringPBEConfig().apply {
            password  = ENCRYPT_PASSWORD
            algorithm = ALGORITHM
            setKeyObtentionIterations("1000")
            setPoolSize("1")
            providerName = "SunJCE"
            setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
            stringOutputType = "base64"
        }

        return PooledPBEStringEncryptor().apply {
            setConfig(config)
        }

    }

}