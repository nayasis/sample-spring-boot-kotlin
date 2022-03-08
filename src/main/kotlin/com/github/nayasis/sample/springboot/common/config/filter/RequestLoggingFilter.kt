package com.github.nayasis.sample.springboot.common.config.filter

import com.github.nayasis.kotlin.basica.core.collection.toJson
import com.github.nayasis.kotlin.basica.core.collection.toString
import com.github.nayasis.kotlin.spring.extension.config.filter.CachedServletRequest
import com.github.nayasis.kotlin.spring.extension.servlet.HttpContext
import com.github.nayasis.kotlin.spring.extension.servlet.HttpContext.Companion.hasProfile
import mu.KotlinLogging
import org.apache.commons.io.IOUtils
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType.*
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.nio.charset.StandardCharsets.UTF_8
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private val log = KotlinLogging.logger {}

@Component
@Order(4)
class RequestLoggingFilter: OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain ) {
        val requestWrapper = CachedServletRequest(request)
        logRequest(requestWrapper)
        filterChain.doFilter(requestWrapper, response)
    }

    private fun logRequest(request: HttpServletRequest) {
        if( !log.isInfoEnabled ) return
        log.info { ">> request: ${RequestInfo(request)}" }
        logRequestBody(request)
        logRequestParameter(request)
    }

    private fun logRequestBody(request: HttpServletRequest) {
        if (HttpContext.hasContentType(MULTIPART_FORM_DATA, MULTIPART_MIXED, MULTIPART_RELATED)) return
        try {
            val body = IOUtils.toString(request.inputStream,UTF_8)
            if (body.isEmpty()) return
            log.info{">> request body\n${body}"}
        } catch (e: IOException) {
            log.error(e.message, e)
        }
    }

    private fun logRequestParameter(request: HttpServletRequest) {
        try {
            val parameters = LinkedHashMap<Any,Any?>()
            request.parameterMap.forEach { (key, values) ->
                when (values.size) {
                    0    -> parameters[key] = null
                    1    -> parameters[key] = values[0]
                    else -> parameters[key] = Arrays.toString(values)
                }
            }
            if( parameters.isEmpty() ) return
            if( hasProfile("local") ) {
                log.info{">> request parameter :\n${parameters.toString(false)}"}
            } else {
                log.info{">> request parameter : ${parameters.toJson()}"}
            }
        } catch (e: Exception) {
            log.error(e.message, e)
        }
    }

}

private data class RequestInfo(
    val ip: String,
    val port: String,
    val protocol: String,
    val method: String,
    val uri: String
) {
    constructor(request: HttpServletRequest): this(
        "${request.remoteAddr}",
        "${request.remotePort}",
        "${request.protocol}",
        "${request.method}",
        "${request.requestURI}",
    )
}