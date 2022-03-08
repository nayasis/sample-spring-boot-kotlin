package com.github.nayasis.sample.springboot.common.config.filter

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(5)
class CorsFilter: OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain ) {

        val referer = getRefererRoot(request.getHeader("referer"))

        response.run {
            setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS")
            setHeader("Access-Control-Max-Age", "3600")
            setHeader("Access-Control-Allow-Headers", "x-requested-with,origin,content-type,accept,Authorization,x-auth-token")
            setHeader("Access-Control-Allow-Origin", referer ?: "*" )
            setHeader("Access-Control-Allow-Credentials", "true")
        }

        filterChain.doFilter(request, response)

    }

    private fun getRefererRoot(referer: String?): String? {
        if( referer.isNullOrEmpty() ) return null
        val firstIndex  = referer.indexOf("://").also { if(it<0) return null }
        val secondIndex = referer.indexOf("/", firstIndex + 3)
        return if (secondIndex < 0) referer else referer.substring(0, secondIndex)
    }

}