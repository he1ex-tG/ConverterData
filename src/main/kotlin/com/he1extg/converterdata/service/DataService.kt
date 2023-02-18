package com.he1extg.converterdata.service

import com.he1extg.converterdata.dto.file.ContentDTO
import com.he1extg.converterdata.dto.file.FilenameDTO

interface DataService {

    fun getFileList(userId: Long): List<FilenameDTO>
    fun getFile(fileId: Long): ContentDTO
    fun setFile(userId: Long, filename: String, content: ByteArray)
}