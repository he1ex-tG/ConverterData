package com.he1extg.converterdata.dto.file

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

class FileUploadDTO(
    @field:NotEmpty(message = "Must be not empty.")
    var content: ByteArray,
    @field:NotBlank(message = "Must be not blank.")
    var filename: String,
    @field:Min(value = 1, message = "Must be greater than zero.")
    var userId: Long
) {
    val contentSize: Int
        get() = content.size
}