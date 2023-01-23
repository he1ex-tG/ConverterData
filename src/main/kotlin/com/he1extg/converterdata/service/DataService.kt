package com.he1extg.converterdata.service

import com.he1extg.converterdata.dto.FilenameBytearrayDTO
import com.he1extg.converterdata.dto.IdFilenameDTO

interface DataService {

    fun getFileList(username: String?): List<IdFilenameDTO>
    fun getFile(converterFileId: Long): FilenameBytearrayDTO
    fun setFile(userName: String, fileName: String, fileByteArray: ByteArray)
}