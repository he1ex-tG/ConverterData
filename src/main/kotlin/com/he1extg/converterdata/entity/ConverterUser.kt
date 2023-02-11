package com.he1extg.converterdata.entity

import javax.persistence.Entity

@Entity
class ConverterUser(
    val username: String,
    val password: String
) : BaseEntity<Long>()