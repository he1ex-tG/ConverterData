package com.he1extg.converterdata.dto.user

import com.he1extg.converterdata.dto.file.FilenameDTO

class UserDTO(
    val id: Long,
    val username: String,
    val files: List<FilenameDTO>
)