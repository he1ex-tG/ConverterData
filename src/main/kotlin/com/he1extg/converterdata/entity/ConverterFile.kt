package com.he1extg.converterdata.entity

import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Lob
import javax.persistence.Table

@Entity
@Table(name = "ConverterFile")
class ConverterFile(
    var filename: String,
    @Lob
    val content: ByteArray,
    var converterUser: String,
    var timestamp: Timestamp = Timestamp.valueOf(LocalDateTime.now()),
) : BaseEntity<Long>()