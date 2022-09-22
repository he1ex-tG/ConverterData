package com.he1extg.converterdata.service

import com.he1extg.converterdata.repository.ConverterFileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConverterUserServiceImpl : ConverterUserService {

    @Autowired
    lateinit var converterFileRepository: ConverterFileRepository

    override fun getUserList(): List<String> {
        return converterFileRepository.findAllConverterUser()
    }
}