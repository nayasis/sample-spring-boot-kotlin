package com.github.nayasis.sample.springboot.common

import com.github.nayasis.sample.springboot.common.Constant.Companion.CACHE.KEY.DEPARTMENT
import com.github.nayasis.sample.springboot.common.Constant.Companion.CACHE.KEY.EMPLOYEE

private const val MIN  = 60
private const val HOUR = 60 * 60

class Constant { companion object {

    const val ENCRYPT_PASSWORD = "SAMPLE"

    object CACHE {
        object KEY {
            const val EMPLOYEE = "EMPLOYEE"
            const val DEPARTMENT = "DEPARTMENT"
        }
        val TTL = mapOf(
            DEPARTMENT to  1 * HOUR,
            EMPLOYEE   to  1 * HOUR,
        )
    }

}}