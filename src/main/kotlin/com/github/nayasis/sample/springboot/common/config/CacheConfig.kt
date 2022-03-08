package com.github.nayasis.sample.springboot.common.config

import com.github.nayasis.kotlin.spring.extension.cache.IgnorableCacheErrorHandler
import com.github.nayasis.kotlin.spring.extension.cache.RedisCacheWriterForClearAll
import com.github.nayasis.kotlin.spring.extension.cache.SimpleKeyGenerator
import com.github.nayasis.sample.springboot.common.Constant.Companion.CACHE.TTL
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.CacheErrorHandler
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder
import org.springframework.data.redis.connection.RedisConnectionFactory
import java.time.Duration

@ConditionalOnProperty(value=["spring.cache.type"], havingValue = "redis")
@EnableCaching
@Configuration
class CacheConfig(

    @Value("\${spring.cache.redis.key-prefix:}")
    private val ENV_KEY_PREFIX: String,
    private val connectionFactory: RedisConnectionFactory

): CachingConfigurerSupport() {

    @Bean
    override fun cacheManager(): RedisCacheManager? {
        val configs = HashMap<String, RedisCacheConfiguration>()
        TTL.forEach { (name: String, ttl: Int) -> configs[name] = ttl(ttl) }
        return RedisCacheManagerBuilder.fromCacheWriter(RedisCacheWriterForClearAll(connectionFactory))
                .cacheDefaults(defaultConfig())
                .withInitialCacheConfigurations(configs).build()
    }

    private fun defaultConfig(): RedisCacheConfiguration {
        val config = RedisCacheConfiguration.defaultCacheConfig()
        return if ( ENV_KEY_PREFIX.isEmpty() ) {
            config.computePrefixWith { cacheName -> "$cacheName::" }
        } else {
            config.computePrefixWith { cacheName -> "$ENV_KEY_PREFIX.$cacheName::" }
        }
    }

    private fun ttl(seconds: Int): RedisCacheConfiguration {
        return defaultConfig().entryTtl(Duration.ofSeconds(seconds.toLong()))
    }

    override fun keyGenerator(): KeyGenerator {
        return SimpleKeyGenerator()
    }

    override fun errorHandler(): CacheErrorHandler {
        return IgnorableCacheErrorHandler()
    }

}