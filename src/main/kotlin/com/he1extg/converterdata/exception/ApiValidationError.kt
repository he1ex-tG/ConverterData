package com.he1extg.converterdata.exception

class ApiValidationError(
    val `object`: String,
    val message: String,
    val field: String,
    val rejectedValue: Any
)