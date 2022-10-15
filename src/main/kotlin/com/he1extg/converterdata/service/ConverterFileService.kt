package com.he1extg.converterdata.service

import com.he1extg.converterdata.dto.converterfile.FilenameBytearrayDTO
import com.he1extg.converterdata.dto.converterfile.IdFilenameDTO

interface ConverterFileService {

    fun getFileList(converterUser: String): List<IdFilenameDTO>
    fun getFile(converterFileId: Long): FilenameBytearrayDTO
    fun setFile(userName: String, fileName: String, fileByteArray: ByteArray)
}