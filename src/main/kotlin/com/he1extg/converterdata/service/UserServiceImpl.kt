package com.he1extg.converterdata.service

import com.he1extg.converterdata.dto.user.AuthenticationDTO
import com.he1extg.converterdata.dto.user.UserDTO
import com.he1extg.converterdata.entity.ConverterUser
import com.he1extg.converterdata.repository.ConverterUserRepository
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class UserServiceImpl(
    private val converterUserRepository: ConverterUserRepository
) : UserService {

    override fun getUserAuthentication(username: String): AuthenticationDTO {
        val userAuthentication = converterUserRepository.getUserAuthenticationByUsername(username).orElseThrow {
            IOException("User with username=\"$username\" not found.")
        }
        return userAuthentication.toAuthenticationDTO()
    }

    override fun getUser(username: String): UserDTO {
        val user = converterUserRepository.getUserByUsername(username).orElseThrow {
            IOException("User with username=\"$username\" not found.")
        }
        return user.toUserDTO()
    }


    override fun addUser(username: String, password: String) {
        val newUser = ConverterUser(
            username = username,
            password = password,
            accountNonExpired = true,
            accountNonLocked = true,
            credentialsNonExpired = true,
            enabled = true
        )
        converterUserRepository.save(newUser)
    }
}