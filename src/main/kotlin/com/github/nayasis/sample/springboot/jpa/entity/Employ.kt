package com.github.nayasis.sample.springboot.jpa.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.nayasis.sample.springboot.jpa.entity.extend.BaseEntity
import com.github.nayasis.sample.springboot.jpa.enum.Position
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
class Employ: BaseEntity() {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long? = null

    @NotNull
    @Column(length = 20)
    var name: String? = null

    @Column(length = 20)
    var job: String? = null

    @Column
    var position: Position? = null

    @Column
    var hireDate: LocalDate? = null

    @Column
    var birthDate: LocalDateTime? = null

    @Column
    var salary: Int = 0

    @JsonIgnore
    @Column
    var departmentId: Long? = null

}