package com.he1extg.converterdata.service

import com.he1extg.converterdata.dto.file.FilenameDTO
import com.he1extg.converterdata.dto.user.AuthenticationDTO
import com.he1extg.converterdata.dto.user.UserDTO
import com.he1extg.converterdata.entity.ConverterUser
import com.he1extg.converterdata.repository.ConverterFileRepository
import com.he1extg.converterdata.repository.ConverterUserRepository
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class UserServiceImpl(
    private val converterUserRepository: ConverterUserRepository,
    private val converterFileRepository: ConverterFileRepository
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
        val files = converterFileRepository.getFileListWithIdAndFilenameByConverterUserId(user.id).map {
            FilenameDTO(it.id, it.filename)
        }
        return UserDTO(user.id, user.username, files)
    }


    override fun addUser(username: String, password: String): UserDTO {
        val newUser = ConverterUser(
            username = username,
            password = password,
            accountNonExpired = true,
            accountNonLocked = true,
            credentialsNonExpired = true,
            enabled = true
        )
        val newUserEntity = converterUserRepository.save(newUser)
        return newUserEntity.toUserDTO()
    }
}