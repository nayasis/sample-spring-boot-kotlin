package com.github.nayasis.sample.springboot.common.config

import com.github.nayasis.kotlin.spring.extension.config.error.Throwables
import com.github.nayasis.sample.springboot.common.exceptions.BizException
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.error.ErrorAttributeOptions.Include.EXCEPTION
import org.springframework.boot.web.error.ErrorAttributeOptions.Include.STACK_TRACE
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest

@Component
class ErrorHandler (
    private val throwables: Throwables
){

    @Bean
    fun errorAttributes(): ErrorAttributes {
        return object : DefaultErrorAttributes() {
            override fun getErrorAttributes(request: WebRequest, options: ErrorAttributeOptions): Map<String,Any> {
                val attributes = super.getErrorAttributes(request, ErrorAttributeOptions.defaults())
                unwrap( getError(request) )?.let{
                    if (options.isIncluded(EXCEPTION))   attributes["exception"] = it.javaClass.name
                    if (options.isIncluded(STACK_TRACE)) attributes["trace"]     = throwables.toString(it)
                    attributes["code"] = when (it) {
                        is BizException -> it.code
                        else -> null
                    }
                    attributes["message"] = it.message ?: it.toString()
                }
                return attributes
            }
        }
    }

    fun toErrorAttribute(exception: Throwable?): ErrorResponse? {
        return unwrap(exception)?.let {
            return ErrorResponse(
                javaClass.name,
                throwables.toString(it),
                when (it) {
                    is BizException -> it.code
                    else -> null
                },
                it.message ?: it.toString(),
            )
        }
    }

    fun unwrap( throwable: Throwable? ): Throwable? =
        with(throwable) {
            when (this?.cause) {
                is BizException -> this.cause!!
                else -> this
            }
        }

}

data class ErrorResponse(
    val exception: String? = null,
    val trace: String?     = null,
    val code: String?      = null,
    val message: String?   = null,
)