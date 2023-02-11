package com.he1extg.converterdata.repository

import com.he1extg.converterdata.dto.UsernamePasswordDTO
import com.he1extg.converterdata.entity.ConverterUser
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface ConverterUserRepository : CrudRepository<ConverterUser, Long> {

    fun getUserByUsername(username: String): Optional<UsernamePasswordDTO>
}