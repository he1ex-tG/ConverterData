package com.he1extg.converterdata.repository

import com.he1extg.converterdata.entity.ConverterFile
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional

interface ConverterFileRepository : CrudRepository<ConverterFile, Long> {
    fun findAllByConverterUser(converterUser: String): List<ConverterFile>
    fun findAllByConverterUserAndFileName(converterUser: String, fileName: String): List<ConverterFile>
}