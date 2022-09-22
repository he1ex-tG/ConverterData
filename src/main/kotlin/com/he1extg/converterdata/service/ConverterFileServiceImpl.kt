package com.he1extg.converterdata.service

import com.he1extg.converterdata.entity.ConverterFile
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
        return converterFileRepository.findAllFileNameByConverterUser(authenticatedUser)
    }

    override fun getFile(fileName: String): ByteArray {
        val converterFile = converterFileRepository.findAllByConverterUserAndFileName(authenticatedUser, fileName)
        if (converterFile.size != 1) {
            throw Exception(
                "File extraction from database encountered with issue. " +
                        "Expected file count = 1. Actually = ${converterFile.size}."
            )
        }
        return converterFile.first()
    }

    override fun setFile(fileName: String, fileByteArray: ByteArray): Boolean {
        val newFile = ConverterFile(fileName, fileByteArray, authenticatedUser)
        try {
            converterFileRepository.save(newFile)
        }
        catch (e: Exception) {
            return false
        }
        return true
    }

}