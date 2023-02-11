package com.he1extg.converterdata.service

import com.he1extg.converterdata.dto.UsernamePasswordDTO

interface UserService {

    fun getUser(username: String): UsernamePasswordDTO
    fun addUser(username: String, password: String)
}