package com.github.nayasis.sample.springboot.common.service.cache

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CacheEvictController(
    private val evictor: CacheEvictor
) {

    @RequestMapping("/evict/employee/{id}")
    fun clearEmployee(@PathVariable id: Long) = evictor.clearEmployee(id)

    @RequestMapping("/evict/employee")
    fun clearEmployee() = evictor.clearEmployee()

    @RequestMapping("/evict/department/{id}")
    fun clearDepartment(@PathVariable id: Long) = evictor.clearDepartment(id)

    @RequestMapping("/evict/department")
    fun clearDepartment() = evictor.clearDepartment()

}