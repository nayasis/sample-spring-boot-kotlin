package com.github.nayasis.sample.springboot.jpa.entity

import com.github.nayasis.sample.springboot.jpa.entity.extend.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Department: BaseEntity() {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long = 0

    @Column(length = 14)
    var name: String? = null

    @Column(length = 50)
    var location: String? = null

}