package com.he1extg.converterdata.dto.user

class AuthenticationDTO(
    val username: String,
    val password: String,
    val accountNonExpired: Boolean,
    val AccountNonLocked: Boolean,
    val credentialsNonExpired: Boolean,
    val enabled: Boolean
)