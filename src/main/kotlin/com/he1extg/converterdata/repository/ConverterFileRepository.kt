package com.he1extg.converterdata.repository

import com.he1extg.converterdata.entity.ConverterFile
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface ConverterFileRepository : CrudRepository<ConverterFile, Long> {
    @Query("select * from ConverterFile where converterUser=:converterUser", nativeQuery = true)
    fun findAllByConverterUser(
        @Param("converterUser") converterUser: String
    ): List<ConverterFile>

    @Query("select converterUser from ConverterFile group by converterUser")
    fun findAllConverterUser(): List<String>
}