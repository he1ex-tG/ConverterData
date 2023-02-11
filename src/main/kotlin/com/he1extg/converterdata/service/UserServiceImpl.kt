package com.he1extg.converterdata.service

import com.he1extg.converterdata.dto.UsernamePasswordDTO
import com.he1extg.converterdata.entity.ConverterUser
import com.he1extg.converterdata.repository.ConverterUserRepository
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class UserServiceImpl(
    private val converterUserRepository: ConverterUserRepository
) : UserService {

    override fun getUser(username: String): UsernamePasswordDTO {
        val converterUser = converterUserRepository.getUserByUsername(username)
        return converterUser.orElseThrow {
            IOException("User with username=\"$username\" not found.")
        }
    }

    override fun addUser(username: String, password: String) {
        val newUser = ConverterUser(
            username = username,
            password = password
        )
        converterUserRepository.save(newUser)
    }
}