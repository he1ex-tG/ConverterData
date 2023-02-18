package com.he1extg.converterdata.dao.user

import com.he1extg.converterdata.dto.user.AuthenticationDTO

data class AuthenticationDAO(
    val username: String,
    val password: String,
    val accountNonExpired: Boolean,
    val AccountNonLocked: Boolean,
    val credentialsNonExpired: Boolean,
    val enabled: Boolean
) {
    fun toAuthenticationDTO(): AuthenticationDTO = AuthenticationDTO(
        username,
        password,
        accountNonExpired,
        AccountNonLocked,
        credentialsNonExpired,
        enabled
    )
}
