package com.he1extg.converterdata.repository

import com.he1extg.converterdata.entity.ConverterFile
import com.he1extg.converterdata.dto.FilenameBytearrayDTO
import com.he1extg.converterdata.dto.IdTimestampDTO
import com.he1extg.converterdata.dto.IdFilenameDTO
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

interface ConverterFileRepository : CrudRepository<ConverterFile, Long> {

    /**
     * Using custom query and class DTO
     */
    @Query("select new com.he1extg.converterdata.dto.IdFilenameDTO(c.id, c.filename) from ConverterFile c where c.converterUser = :converterUser")
    fun getConverterFileListByConverterUser(
        @Param("converterUser") converterUser: String
    ): List<IdFilenameDTO>

    /**
     * Using Transactional and class DTO
     */
    @Transactional
    fun getConverterFileById(id: Long): Optional<FilenameBytearrayDTO>

    /**
     * Using auto built query and interface DTO
     * Query looks like this: @Query("select c.id as id, c.timestamp as timestamp from ConverterFile c where c.converterUser = :converterUser")
     */
    fun getConverterFileTimestampByConverterUser(converterUser: String): List<IdTimestampDTO>
}