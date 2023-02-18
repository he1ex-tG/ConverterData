package com.he1extg.converterdata.dao.file

import com.he1extg.converterdata.dto.file.FilenameDTO

data class FilenameDAO(
    val id: Long,
    val filename: String
) {
    fun toFilenameDTO(): FilenameDTO = FilenameDTO(id, filename)
}
