package com.github.nayasis.sample.springboot.biz.employ

import com.github.nayasis.kotlin.basica.annotation.NoArg
import com.github.nayasis.kotlin.spring.extension.jpa.domain.BasePageParam
import com.github.nayasis.sample.springboot.jpa.entity.Department
import com.github.nayasis.sample.springboot.jpa.entity.Employ
import com.github.nayasis.sample.springboot.jpa.enum.Position
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class EmployController {

    @PostMapping("/employ")
    fun saveUser(@RequestBody req: ReqEmploy) {

    }

    @DeleteMapping("/employ/{id}")
    fun deleteUser(id: Long) {

    }

    @GetMapping("/employ/{id}")
    fun getUser(@PathVariable id: Long) {

    }

    @GetMapping("/employ/all")
    fun findUsers(@RequestBody req: QryEmploy) {

    }

}

@NoArg
data class ReqEmploy(
    var id: Long?,
    var name: String?,
    var job: String?,
    var position: Position?,
    var hireDate: LocalDate?,
    var salary: Int?,
    var departmentId: Long?
)

class ResEmployee: Employ {
    var department: Department? = null
    constructor()
    constructor(employ: Employ) {
        this.id       = employ.id
        this.name     = employ.name
        this.job      = employ.job
        this.position = employ.position
        this.hireDate = employ.hireDate
        this.salary   = employ.salary
        this.regId    = employ.regId
        this.regDt    = employ.regDt
        this.updId    = employ.updId
        this.updDt    = employ.updDt
    }
}

@NoArg
data class QryEmploy(
    var id: List<Long>?,
    var name: String?,
    var job: String?,
    var position: List<Position>?,
    var departmentId: List<Long>?,
    var fromHireDate: LocalDate?,
    var toHireDate: LocalDate?,
    var fromSalary: Int?,
    var toSalary: Int?,
    var departmentName: String?,
    var departmentLocation: String?,
    var page: BasePageParam = BasePageParam()
)