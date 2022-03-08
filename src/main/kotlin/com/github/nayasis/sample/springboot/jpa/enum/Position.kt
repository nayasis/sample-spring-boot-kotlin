package com.github.nayasis.sample.springboot.jpa.enum

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class Position(
    val cd: String,
    val desc: String,
) {

    HEAD     ( "CD001", "Head"),
    MANAGER  ( "CD002", "Manager"),
    EMPLOYEE ( "CD003", "Employee"),
    ;

    companion object {
        @JsonCreator
        @JvmStatic
        fun of( code: String? ): Position? {
            if( code.isNullOrEmpty() ) return null;
            return values().firstOrNull { e -> e.cd == code }
        }
    }

}