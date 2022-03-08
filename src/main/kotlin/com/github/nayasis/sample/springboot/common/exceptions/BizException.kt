package com.github.nayasis.sample.springboot.common.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.FORBIDDEN)
class BizException: RuntimeException {

    var code: String? = null
        private set

    constructor()

    constructor(message: String,code: String? = null): super(message) {
        this.code = code
    }

    constructor(message: String, cause: Throwable, code: String? = null): super(message,cause) {
        this.code = code
    }

    constructor(cause: Throwable): super(cause)

}


