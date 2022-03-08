package com.github.nayasis.sample.springboot.jpa.repository

import com.github.nayasis.sample.springboot.jpa.entity.Department
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface DepartmentRepository: JpaRepository<Department,Long>, JpaSpecificationExecutor<Department>