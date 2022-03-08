@file:Suppress("SpringJavaInjectionPointsAutowiringInspection")

package com.github.nayasis.sample.springboot.common.lock

import com.github.nayasis.sample.springboot.common.exceptions.BizException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.integration.support.locks.LockRegistry
import org.springframework.orm.ObjectOptimisticLockingFailureException
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.locks.Lock

private val logger = KotlinLogging.logger {}

@Configuration
class Locker(
    val lockRegistry: LockRegistry,
    @Value("\${distributed-lock.timeout.connection:1000}")
    val connectionTimeout: Long,
) {

    fun <T> lock(lockKey: String, fn:() -> T): T {
        val lock = tryLock(lockKey)
        try {
            return fn()
        } catch ( e: ObjectOptimisticLockingFailureException ) {
            logger.error(e.message,e)
            throw BizException(e)
        } catch (e: Throwable) {
            throw e
        } finally {
            try {
                lock.unlock()
            } catch (e: Exception) {
                logger.error(e.message,e)
            }
        }

    }

    private fun tryLock(lockKey: String): Lock {
        val lock   = lockRegistry.obtain(lockKey)
        if( lock.tryLock(connectionTimeout, MILLISECONDS) )
            return lock
        throw BizException("Fail to acquire lock (key:$lockKey)")
    }

}