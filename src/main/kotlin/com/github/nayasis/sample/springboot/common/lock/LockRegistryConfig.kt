package com.github.nayasis.sample.springboot.common.lock

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.integration.redis.util.RedisLockRegistry
import org.springframework.integration.support.locks.ExpirableLockRegistry
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.util.StopWatch

private val log = KotlinLogging.logger {}

@Configuration
class LockRegistryConfig(
    @Value("\${distributed-lock.timeout.session:30000}")
    val sessionTimeout: Int,
    @Value("\${distributed-lock.key-prefix:}")
    val prefix: String,
) {

    @Bean
    fun redisLockRegistry(
        connectionFactory: RedisConnectionFactory,
    ): RedisLockRegistry {
        return RedisLockRegistry(connectionFactory, prefix, sessionTimeout.toLong())
            .also { log.info { "Redis LockRegistry activated." } }
    }

}


@Configuration
@ConditionalOnBean(ExpirableLockRegistry::class)
@ConditionalOnExpression("\${distributed-lock.expire.enable:true}")
class LockScheduler(
    private val lockRegistry: ExpirableLockRegistry,
    @Value("\${distributed-lock.expire.age:60000}")
    private val expireAge: Int,
) {

    @Scheduled(cron="\${distributed-lock.expire.cron:0 * * * * *}")
    fun expireUnusedLock() {
        StopWatch("Clear unused lock").let {
            it.start()
            lockRegistry.expireUnusedOlderThan(expireAge.toLong())
            log.debug { it.shortSummary() }
        }
    }

}