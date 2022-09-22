package com.he1extg.converterdata.service

interface ConverterFileService {

    fun getFileList(): List<String>
    fun getFile(fileName: String): ByteArray
    fun setFile(fileName: String, fileByteArray: ByteArray): Boolean
}