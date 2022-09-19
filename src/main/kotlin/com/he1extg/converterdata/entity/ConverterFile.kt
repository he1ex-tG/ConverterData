package com.he1extg.converterdata.entity

import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.Table

@Entity
@Table(name = "ConverterFile")
class ConverterFile(
    var fileName: String,
    @Lob val file: ByteArray,
    var converterUser: String,
    var timestamp: Timestamp = Timestamp.valueOf(LocalDateTime.now()),
    @Id @GeneratedValue var id: Long? = null
)