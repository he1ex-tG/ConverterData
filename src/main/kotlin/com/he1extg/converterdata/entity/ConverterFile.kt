package com.he1extg.converterdata.entity

import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.Lob
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "ConverterFile")
class ConverterFile(
    var filename: String,
    @Lob
    val content: ByteArray,
    var timestamp: Timestamp = Timestamp.valueOf(LocalDateTime.now()),
    @ManyToOne
    @JoinColumn(name = "converter_user_id")
    val converterUser: ConverterUser
) : BaseEntity<Long>()