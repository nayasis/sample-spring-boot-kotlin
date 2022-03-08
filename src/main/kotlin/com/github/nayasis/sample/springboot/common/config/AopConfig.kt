package com.github.nayasis.sample.springboot.common.config

import com.github.nayasis.kotlin.basica.etc.StopWatch
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Aspect
@Component
class AopConfig {

    @Around( """
        @annotation(org.springframework.web.bind.annotation.RequestMapping) ||
        @annotation(org.springframework.web.bind.annotation.PostMapping)    ||
        @annotation(org.springframework.web.bind.annotation.GetMapping)     ||
        @annotation(org.springframework.web.bind.annotation.PutMapping)     ||
        @annotation(org.springframework.web.bind.annotation.PatchMapping)   ||
        @annotation(org.springframework.web.bind.annotation.DeleteMapping)
    """ )
    @Throws(Throwable::class)
    fun logging( joinPoint: ProceedingJoinPoint ): Any? {

        logTargetController(joinPoint)

        val watcher = StopWatch()
        var success = true

        try {
            return joinPoint.proceed()
        } catch (e: Throwable) {
            success = false
            throw e
        } finally {
            log.debug{ ">> Request End (${if (success) "success" else "failed"}, ${calledMethod(joinPoint)}, ${watcher.elapsedMillis}ms)" }
        }

    }

    private fun logTargetController(joinPoint: JoinPoint) {
        log.debug{ ">> Request controller\n${calledMethod(joinPoint)}" }
    }

    private fun calledMethod(joinPoint: JoinPoint): String {
        return joinPoint.toString()
                .replaceFirst("^execution\\(".toRegex(), "")
                .replaceFirst("\\)$".toRegex(), "")
                .replaceFirst("^(.+?) (.+?)$".toRegex(), "<$1> $2")
    }

}

