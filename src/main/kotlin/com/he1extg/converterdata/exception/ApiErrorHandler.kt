package com.he1extg.converterdata.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

private const val PREFIX_PROJECT_NAME = "ConverterData"

@ControllerAdvice
class ApiErrorHandler : ResponseEntityExceptionHandler() {

    /**
     * ResponseEntity builder
     */
    private fun buildResponseEntity(apiError: ApiError): ResponseEntity<ApiError> {
        return ResponseEntity(apiError, apiError.status)
    }

    /**
     * Default exception handlers
     */

    /**
     * Custom exception handlers
     */
    @ExceptionHandler(NoFileInDatabase::class)
    fun handlerConverterEmptyStringException(ex: NoFileInDatabase): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST, "${PREFIX_PROJECT_NAME}: ${ex.message}", ex)
        return buildResponseEntity(apiError)
    }

    /**
     * Database module exception handlers
     */
}