package com.he1extg.converterdata.repository

import com.he1extg.converterdata.entity.ConverterFile
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface ConverterFileRepository : CrudRepository<ConverterFile, Long> {

    @Transactional
    @Query("select c from ConverterFile c where c.converterUser = :converterUser")
    fun findAllByConverterUser(
        @Param("converterUser") converterUser: String
    ): List<ConverterFile>

    @Query("select converterUser from ConverterFile group by converterUser")
    fun findAllConverterUser(): List<String>
}