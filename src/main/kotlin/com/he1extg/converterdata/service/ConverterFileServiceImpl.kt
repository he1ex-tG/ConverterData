package com.he1extg.converterdata.service

import com.he1extg.converterdata.repository.ConverterFileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class ConverterFileServiceImpl : ConverterFileService {

    @Autowired
    lateinit var converterFileRepository: ConverterFileRepository

    val authenticatedUser: String
        get() = SecurityContextHolder.getContext().authentication.name

    override fun getFileList(): List<String> {
        return converterFileRepository.findAllByConverterUser(authenticatedUser).map { it.fileName }
    }

}