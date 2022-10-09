package com.he1extg.converterdata.dto

interface ConverterFileDto {
    val fileName: String
    val file: ByteArray
    val converterUser: String
    val id: Long?
}
