package com.he1extg.converterdata.entity

import javax.persistence.Entity

@Entity
class ConverterUser(
    val username: String,
    val password: String,
    val accountNonExpired: Boolean,
    val AccountNonLocked: Boolean,
    val credentialsNonExpired: Boolean,
    val enabled: Boolean
) : BaseEntity<Long>()