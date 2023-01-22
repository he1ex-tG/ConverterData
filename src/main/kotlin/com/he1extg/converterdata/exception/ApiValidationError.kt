package com.he1extg.converterdata.exception

class ApiValidationError(
    private val `object`: String,
    private val message: String,
) : ApiSubError() {

    private var field: String? = null
    private var rejectedValue: Any? = null

    constructor(
        `object`: String,
        field: String,
        rejectedValue: Any,
        message: String
    ) : this(`object`, message) {
        this.field = field
        this.rejectedValue = rejectedValue
    }

}