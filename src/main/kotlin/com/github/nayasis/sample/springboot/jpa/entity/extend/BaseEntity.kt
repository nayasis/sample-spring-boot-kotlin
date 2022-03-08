package com.github.nayasis.sample.springboot.jpa.entity.extend

import com.github.nayasis.kotlin.basica.reflection.Reflector
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
class BaseEntity : Serializable {

    @CreationTimestamp
    @Column
    var regDt: LocalDateTime? = null

    @Column(length=10)
    var regId: String? = null
        set(value) {
            field = value ?: "system"
        }

    @UpdateTimestamp
    @Column
    var updDt: LocalDateTime? = null

    @Column(length=10)
    var updId: String? = null
        set(value) {
            field = value
            if( regId.isNullOrEmpty() ) {
                regId = value
            }
        }

    override fun toString(): String {
        return Reflector.toJson(this)
    }

}