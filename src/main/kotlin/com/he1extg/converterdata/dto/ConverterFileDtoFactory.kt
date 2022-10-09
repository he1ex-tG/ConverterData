package com.he1extg.converterdata.dto

import com.he1extg.converterdata.entity.ConverterFile

class ConverterFileDtoFactory(
    private val converterFile: ConverterFile
) {

    val fileList: ConverterFileDto
        get() = object : ConverterFileDto {
            override val fileName: String
                get() = converterFile.fileName
            override val file: ByteArray
                get() = byteArrayOf()
            override val converterUser: String
                get() = ""
            override val id: Long?
                get() = converterFile.id
        }

    val file: ConverterFileDto
        get() = object : ConverterFileDto {
            override val fileName: String
                get() = converterFile.fileName
            override val file: ByteArray
                get() = converterFile.file
            override val converterUser: String
                get() = converterFile.converterUser
            override val id: Long?
                get() = converterFile.id
        }
}