package com.github.nayasis.sample.springboot.biz.test

import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val testService: TestService,
) {

    @PutMapping("/test/init")
    fun initDummyData() {
        testService.initDummyData()
    }

}