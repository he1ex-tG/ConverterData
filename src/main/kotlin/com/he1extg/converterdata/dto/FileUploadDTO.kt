package com.he1extg.converterdata.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

class FileUploadDTO(
    @field:NotEmpty(message = "Must be not empty.")
    var content: ByteArray,
    @field:NotBlank(message = "Must be not blank.")
    var filename: String,
    @field:NotBlank(message = "Must be not blank.")
    var username: String,
) {
    val contentSize: Int
        get() = content.size
}