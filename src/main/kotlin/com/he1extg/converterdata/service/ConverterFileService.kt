package com.he1extg.converterdata.service

import com.he1extg.converterdata.entity.dto.CFFnameBarrayDto
import com.he1extg.converterdata.entity.dto.CFIdFnameDto

interface ConverterFileService {

    fun getFileList(converterUser: String): List<CFIdFnameDto>
    fun getFile(converterFileId: Long): CFFnameBarrayDto
    fun setFile(userName: String, fileName: String, fileByteArray: ByteArray)
}