package com.he1extg.converterdata.repository

import com.he1extg.converterdata.entity.ConverterFile
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface ConverterFileRepository : CrudRepository<ConverterFile, Long> {
    @Query("select id,fileName from ConverterFile where converterUser=:converterUser")
    fun findAllIdAndFileNameByConverterUser(
        @Param("converterUser") converterUser: String
    ): Map<Long, String>

    @Transactional
    @Query("select file from ConverterFile where converterUser=:converterUser and fileName=:fileName")
    fun findAllByConverterUserAndFileName(
        @Param("converterUser") converterUser: String,
        @Param("fileName") fileName: String
    ): List<ByteArray>

    @Query("select converterUser from ConverterFile group by converterUser")
    fun findAllConverterUser(): List<String>
}