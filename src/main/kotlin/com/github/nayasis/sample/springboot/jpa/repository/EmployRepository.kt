package com.github.nayasis.sample.springboot.jpa.repository

import com.github.nayasis.sample.springboot.jpa.entity.Employ
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployRepository: JpaRepository<Employ,Long> {

    fun countAllByDepartmentId(departmentId: Long): Int
}