package com.he1extg.converterdata.repository

import com.he1extg.converterdata.entity.ConverterFile
import com.he1extg.converterdata.entity.dto.CFFnameBarrayDto
import com.he1extg.converterdata.entity.dto.CFIdFnameDto
import com.he1extg.converterdata.entity.dto.CFIdTstampDto
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

interface ConverterFileRepository : CrudRepository<ConverterFile, Long> {

    /**
     * Using custom query and DTO
     */
    @Query("select c.id as id, c.fileName as fileName from ConverterFile c where c.converterUser = :converterUser")
    fun getConverterFileListByConverterUser(
        @Param("converterUser") converterUser: String
    ): List<CFIdFnameDto>

    /**
     * Using Transactional, custom query and DTO
     */
    @Transactional
    @Query("select c.file as file, c.fileName as fileName from ConverterFile c where c.id = :id")
    fun getConverterFileById(
        @Param("id") id: Long
    ): Optional<CFFnameBarrayDto>

    /**
     * Using auto built query and DTO
     */
    fun getConverterFileTimestampByConverterUser(converterUser: String): List<CFIdTstampDto>
}