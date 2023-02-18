package com.he1extg.converterdata.repository

import com.he1extg.converterdata.dao.file.ContentDAO
import com.he1extg.converterdata.dao.file.FilenameDAO
import com.he1extg.converterdata.dao.file.TimestampDAO
import com.he1extg.converterdata.entity.ConverterFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

interface ConverterFileRepository : JpaRepository<ConverterFile, Long> {

    /**
     * Using custom query and class DTO
     *
    @Query("select new com.he1extg.converterdata.dto.IdFilenameDTO(c.id, c.filename) from ConverterFile c where c.converterUser = :converterUser")
    fun getConverterFileListByConverterUser(
        @Param("converterUser") converterUser: String
    ): List<FilenameDTO>
    */

    /**
     * Using Transactional and class DTO
     *
    @Transactional
    fun getConverterFileById(id: Long): Optional<ContentDTO>
    */

    /**
     * Using auto built query and interface DTO
     * Query looks like this: @Query("select c.id as id, c.timestamp as timestamp from ConverterFile c where c.converterUser = :converterUser")
     *
    fun getConverterFileTimestampByConverterUser(converterUser: String): List<IdTimestampDTO>
    */

    /**
     * New generation
     */
    fun getFileListWithIdAndFilenameByConverterUserId(id: Long): List<FilenameDAO>
    fun getFileListWithIdAndTimestampByConverterUserId(id: Long): List<TimestampDAO>
    @Transactional
    fun getFileContentById(id: Long): Optional<ContentDAO>
}