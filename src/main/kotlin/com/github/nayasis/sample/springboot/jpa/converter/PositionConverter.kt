package com.github.nayasis.sample.springboot.jpa.converter

import com.github.nayasis.sample.springboot.jpa.enum.Position
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class PositionConverter: AttributeConverter<Position,String> {
    override fun convertToDatabaseColumn(attribute: Position?): String? = attribute?.cd
    override fun convertToEntityAttribute(code: String?): Position? = code?.let { Position.of(code) }
}