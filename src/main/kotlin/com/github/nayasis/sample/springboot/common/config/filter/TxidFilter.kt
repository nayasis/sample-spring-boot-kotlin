package com.github.nayasis.sample.springboot.common.config.filter

import com.github.nayasis.kotlin.basica.core.extention.ifEmpty
import com.github.nayasis.kotlin.basica.etc.error
import mu.KotlinLogging
import org.slf4j.MDC
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val KEY_GUID = "GUID"

private val log = KotlinLogging.logger {}

@Component
@Order(1)
class TxidFilter: OncePerRequestFilter() {

    override fun doFilterInternal( request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain ) {
        setGuid(request)
        try {
            filterChain.doFilter(request, response)
        } finally {
            clearGuid()
        }
    }

    private fun setGuid(request: HttpServletRequest) {
        runCatching {
            var guid = request.getHeader("x-guid").ifEmpty { generateGuid() }
            MDC.put(KEY_GUID, guid)
        }.onFailure { log.error(it) }
    }

    private fun clearGuid() {
        runCatching {
            MDC.remove(KEY_GUID)
        }
    }

    private fun generateGuid(): String {
        return "${System.currentTimeMillis()}-${UUID.randomUUID()}"
    }

}
