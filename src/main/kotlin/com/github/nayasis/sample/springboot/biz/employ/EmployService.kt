package com.github.nayasis.sample.springboot.biz.employ

import com.github.nayasis.kotlin.basica.core.extention.ifEmpty
import com.github.nayasis.kotlin.basica.core.extention.ifNotEmpty
import com.github.nayasis.sample.springboot.biz.department.DepartmentService
import com.github.nayasis.sample.springboot.jpa.entity.Department
import com.github.nayasis.sample.springboot.jpa.entity.Employ
import com.github.nayasis.sample.springboot.jpa.repository.EmployRepository
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.pageQuery
import com.linecorp.kotlinjdsl.spring.data.updateQuery
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class EmployService(
    private val employRepository: EmployRepository,
    private val departmentService: DepartmentService,
    private val query: SpringDataQueryFactory,
) {

    @Transactional
    fun saveEmployee(req: ReqEmploy): Employ {
        return req.id?.let { employRepository.findByIdOrNull(it) }.ifEmpty {
            employRepository.save(Employ().apply {
                hireDate = LocalDate.now()
            })
        }.apply {
            req.name.ifNotEmpty { name = it }
            req.job.ifNotEmpty { job = it }
            req.position.ifNotEmpty { position = it }
            req.hireDate.ifNotEmpty { hireDate = it }
            req.salary.ifNotEmpty { salary = it }
            req.departmentId.ifNotEmpty { departmentId = it }
        }
    }

    @Transactional
    fun deleteEmployee(id: Long): Boolean {
        return employRepository.findByIdOrNull(id)?.let {
            employRepository.delete(it)
            true
        } ?: false
    }

    @Transactional
    fun detachDepartment(departmentId: Long): Int {
        return query.updateQuery<Employ> {
            set(col(Employ::departmentId), null)
            where(col(Employ::departmentId).equal(departmentId))
        }.executeUpdate()
    }

    fun getEmployee(id: Long): ResEmployee? {
        return employRepository.findByIdOrNull(id)?.let { ResEmployee(it).apply {
            department = departmentService.getDepartment(it.departmentId)
        }}
    }

    fun findAllBy(req: QryEmploy): Page<ResEmployee> {
        return query.pageQuery<ResEmployee>(req.page.toPageable("${Employ::name}", Employ::class)) {
            select(entity(Employ::class))
            from(entity(Employ::class))
            join(entity(Department::class),on {
                col(Employ::departmentId).isNull() or
                    col(Employ::departmentId).equal(col(Department::id))
            })
            req.id.ifNotEmpty { where(col(Employ::id).`in`(it)) }
            req.name.ifNotEmpty { where(col(Employ::name).like("%${it}%")) }
            req.job.ifNotEmpty { where(col(Employ::name).like("%${it}%")) }
            req.position.ifNotEmpty { where(col(Employ::position).`in`(it)) }
            req.departmentId.ifNotEmpty { where(col(Employ::departmentId).`in`(it)) }
            req.fromHireDate.ifNotEmpty { where(col(Employ::hireDate).greaterThanOrEqualTo(it)) }
            req.toHireDate.ifNotEmpty { where(col(Employ::hireDate).lessThanOrEqualTo(it)) }
            req.fromSalary.ifNotEmpty { where(col(Employ::salary).greaterThanOrEqualTo(it)) }
            req.toSalary.ifNotEmpty { where(col(Employ::salary).lessThanOrEqualTo(it)) }
            req.departmentName.ifNotEmpty { where(col(Department::name).like("%${it}%")) }
            req.departmentLocation.ifNotEmpty { where(col(Department::location).like("%${it}%")) }
        }.apply {
            content.forEach {
                it.department = departmentService.getDepartment(it.departmentId)
            }
        }
    }

}

