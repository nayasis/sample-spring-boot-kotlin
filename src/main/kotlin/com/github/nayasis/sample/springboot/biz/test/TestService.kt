package com.github.nayasis.sample.springboot.biz.test

import com.github.nayasis.kotlin.basica.core.localdate.toLocalDate
import com.github.nayasis.sample.springboot.jpa.entity.Department
import com.github.nayasis.sample.springboot.jpa.entity.Employ
import com.github.nayasis.sample.springboot.jpa.enum.Position.*
import com.github.nayasis.sample.springboot.jpa.repository.DepartmentRepository
import com.github.nayasis.sample.springboot.jpa.repository.EmployRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TestService(
    private val employRepository: EmployRepository,
    private val departmentRepository: DepartmentRepository,
) {

    @Transactional
    fun initDummyData() {

        employRepository.deleteAll()
        departmentRepository.deleteAll()

        val departments = listOf(
            Department().apply { name = "HR"; location = "Sungnam" }      ,
            Department().apply { name = "Sales"; location = "Dongtan" }   ,
        ).also { departmentRepository.saveAll(it) }

        listOf(
            Employ().apply { name = "nayasis"; job = "head"; position = HEAD; hireDate = "2021-01-03".toLocalDate(); salary = 2000; departmentId = departments[0].id },
            Employ().apply { name = "jake"; job = "consultant"; position = MANAGER; hireDate = "2021-01-03".toLocalDate(); salary = 1000; departmentId = departments[0].id },
            Employ().apply { name = "nathen"; job = "part"; position = EMPLOYEE; hireDate = "2021-02-01".toLocalDate(); salary = 500; departmentId = departments[0].id },
            Employ().apply { name = "tomas"; job = "sales"; position = MANAGER; hireDate = "2021-05-03".toLocalDate(); salary = 700; departmentId = departments[1].id },
        ).let { employRepository.saveAll(it) }

    }

}