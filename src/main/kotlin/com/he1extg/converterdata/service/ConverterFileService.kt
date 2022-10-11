package com.he1extg.converterdata.service

import com.he1extg.converterdata.entity.dto.CfFilenameAndByteArrayDto
import com.he1extg.converterdata.entity.dto.CfIdAndFilenameDto

interface ConverterFileService {

    fun getFileList(converterUser: String): List<CfIdAndFilenameDto>
    fun getFile(converterFileId: Long): CfFilenameAndByteArrayDto
    fun setFile(userName: String, fileName: String, fileByteArray: ByteArray)
}