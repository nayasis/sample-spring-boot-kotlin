package com.github.nayasis.sample.springboot.biz.department

import com.github.nayasis.kotlin.basica.core.extention.ifEmpty
import com.github.nayasis.kotlin.basica.core.extention.ifNotEmpty
import com.github.nayasis.sample.springboot.biz.employ.EmployService
import com.github.nayasis.sample.springboot.jpa.entity.Department
import com.github.nayasis.sample.springboot.jpa.repository.DepartmentRepository
import com.github.nayasis.sample.springboot.jpa.repository.EmployRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DepartmentService(
    private val departmentRepository: DepartmentRepository,
    private val employRepository: EmployRepository,
    private val employService: EmployService,
) {

    @Transactional
    fun saveDepartment(req: ReqDepartment): Department {
        return req.id?.let { departmentRepository.findByIdOrNull(it) }.ifEmpty {
            departmentRepository.save(Department())
        }.apply {
            req.name.ifNotEmpty { name = it }
            req.location.ifNotEmpty { location = it }
        }
    }

    @Transactional
    fun deleteDepartment(id: Long): Int {
        return departmentRepository.findByIdOrNull(id)?.let { entity ->
            departmentRepository.delete(entity)
            employService.detachDepartment(id)
        } ?: 0
    }

    fun getDepartment(id: Long?): Department? {
        return id?.let{ departmentRepository.findByIdOrNull(it) }
    }

    fun findAllBy(req: QryDepartment): List<ResDepartmentDetail> {
        return departmentRepository.findAll(req.specification(), req.sort())
            .map { ResDepartmentDetail(it).apply {
                employCount = employRepository.countAllByDepartmentId(it.id)
            }}
    }

}