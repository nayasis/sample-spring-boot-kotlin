package com.github.nayasis.sample.springboot.biz.department

import com.github.nayasis.kotlin.basica.annotation.NoArg
import com.github.nayasis.kotlin.basica.core.extention.ifNotEmpty
import com.github.nayasis.kotlin.spring.extension.jpa.domain.SortBuilder
import com.github.nayasis.kotlin.spring.extension.jpa.specification.`in`
import com.github.nayasis.kotlin.spring.extension.jpa.specification.and
import com.github.nayasis.kotlin.spring.extension.jpa.specification.like
import com.github.nayasis.sample.springboot.jpa.entity.Department
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DepartmentController(
    private val departmentService: DepartmentService,
) {

    @PostMapping("/department")
    fun saveDepartment(@RequestBody req: ReqDepartment) {
        departmentService.saveDepartment(req)
    }

    @DeleteMapping("/department/{id}")
    fun deleteDepartment(@PathVariable id: Long) {
        departmentService.deleteDepartment(id)
    }

    @GetMapping("/department/{id}")
    fun getDepartment(@PathVariable id: Long): Department? {
        return departmentService.getDepartment(id)
    }

    @GetMapping("/department/all")
    fun findDepartments(@RequestBody req: QryDepartment): List<ResDepartmentDetail> {
        return departmentService.findAllBy(req)
    }

}

@NoArg
data class ReqDepartment(
    var id: Long? = null,
    var name: String? = null,
    var location: String? = null
)

class ResDepartmentDetail: Department {

    var employCount: Int = 0

    constructor(department: Department) {
        this.id       = department.id
        this.name     = department.name
        this.location = department.location
        this.regId    = department.regId
        this.regDt    = department.regDt
        this.updId    = department.updId
        this.updDt    = department.updDt
    }

}

@NoArg
data class QryDepartment(
    var id: List<Long>?,
    var name: List<String>?,
    var location: List<String>?,
    var sort: String? = null,
) {

    fun sort(): Sort = SortBuilder().toSort("${Department::name}", Department::class)

    fun specification(): Specification<Department> {
        return and(
            id.ifNotEmpty { Department::id.`in`(it) },
        ).apply {
            name.ifNotEmpty { words ->
                words.forEach { and( Department::name.like(it) ) }
            }
            location.ifNotEmpty { words ->
                words.forEach { and( Department::location.like(it) ) }
            }
        }
    }

}

