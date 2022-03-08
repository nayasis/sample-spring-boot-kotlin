package com.github.nayasis.sample.springboot.common.config.filter

import com.github.nayasis.kotlin.basica.thread.NThreadLocal
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(3)
class ThreadLocalFilter : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain ) {
        try {
            filterChain.doFilter(request,response)
        } finally {
            NThreadLocal.clear()
        }
    }
}