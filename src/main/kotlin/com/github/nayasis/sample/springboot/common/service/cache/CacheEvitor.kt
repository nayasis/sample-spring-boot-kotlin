package com.github.nayasis.sample.springboot.common.service.cache

import com.github.nayasis.sample.springboot.common.Constant.Companion.CACHE.KEY.DEPARTMENT
import com.github.nayasis.sample.springboot.common.Constant.Companion.CACHE.KEY.EMPLOYEE
import org.springframework.cache.annotation.CacheEvict
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
interface CacheEvictor {

    @Async
    @CacheEvict(EMPLOYEE)
    fun clearEmployee(id: Long)

    @Async
    @CacheEvict(EMPLOYEE, allEntries = true)
    fun clearEmployee()

    @Async
    @CacheEvict(DEPARTMENT)
    fun clearDepartment(id: Long)

    @Async
    @CacheEvict(DEPARTMENT, allEntries = true)
    fun clearDepartment()

}