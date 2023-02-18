package com.he1extg.converterdata.dao.user

import com.he1extg.converterdata.dto.user.UserDTO

data class UserDAO(
    val id: Long,
    val username: String
) {

    fun toUserDTO(): UserDTO = UserDTO(id, username)
}