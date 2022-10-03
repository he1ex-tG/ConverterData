package com.he1extg.converterdata.dto

data class ConverterFileDto(
    val fileName: String = "",
    val file: ByteArray = byteArrayOf(),
    val converterUser: String = "",
    val id: Long? = null,
)
