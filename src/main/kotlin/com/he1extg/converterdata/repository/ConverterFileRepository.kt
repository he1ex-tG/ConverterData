package com.he1extg.converterdata.repository

import com.he1extg.converterdata.entity.ConverterFile
import org.springframework.data.repository.CrudRepository

interface ConverterFileRepository : CrudRepository<ConverterFile, Long> {
    fun findAllByConverterUser(converterUser: String): List<ConverterFile>
}