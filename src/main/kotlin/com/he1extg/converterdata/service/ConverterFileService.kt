package com.he1extg.converterdata.service

import com.he1extg.converterdata.entity.ConverterFile

interface ConverterFileService {

    fun getFileList(userName: String): List<ConverterFile>
    fun getFile(fileId: Long): ByteArray
    fun setFile(userName: String, fileName: String, fileByteArray: ByteArray): Boolean
}