package com.he1extg.converterdata.dto

import javax.validation.constraints.NotBlank

class UsernamePasswordDTO(
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String
)