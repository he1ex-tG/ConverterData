package com.he1extg.converterdata.dto.user

import javax.validation.constraints.NotBlank

class NewUserDTO(
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String
)