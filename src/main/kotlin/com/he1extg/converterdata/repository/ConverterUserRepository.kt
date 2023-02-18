package com.he1extg.converterdata.repository

import com.he1extg.converterdata.dao.user.AuthenticationDAO
import com.he1extg.converterdata.dao.user.UserDAO
import com.he1extg.converterdata.entity.ConverterUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ConverterUserRepository : JpaRepository<ConverterUser, Long> {

    fun getUserAuthenticationByUsername(username: String): Optional<AuthenticationDAO>
    fun getUserByUsername(username: String): Optional<UserDAO>
}