package com.he1extg.converterdata.dto

class FileUploadDTO(
    var content: ByteArray,
    var name: String,
    var user: String,
) {
    val contentSize: Int
        get() = content.size
}