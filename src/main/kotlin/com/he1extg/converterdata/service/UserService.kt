package com.he1extg.converterdata.service

import com.he1extg.converterdata.dto.user.AuthenticationDTO
import com.he1extg.converterdata.dto.user.UserDTO

interface UserService {

    fun getUserAuthentication(username: String): AuthenticationDTO
    fun getUser(username: String): UserDTO
    fun addUser(username: String, password: String): UserDTO
}