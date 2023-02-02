package com.he1extg.converterdata.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class DataErrorHandler : ResponseEntityExceptionHandler() {

    /**
     * ResponseEntity builder
     */
    private fun buildResponseEntity(apiError: ApiError): ResponseEntity<Any> {
        return ResponseEntity(apiError, apiError.status)
    }

    /**
     * Default exception handlers
     */
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ApiError(
            status = HttpStatus.BAD_REQUEST,
            message = "Controller ${ex.parameter.executable.name} method fails with validation exception: " +
                    "an invalid argument was passed in ${ex.objectName} parameter.",
            debugMessage = ex.localizedMessage,
            subErrors = ex.fieldErrors.fold(mutableListOf<ApiValidationError>()) { acc, fieldError ->
                acc.add(
                    ApiValidationError(
                        `object` = fieldError.objectName,
                        message = fieldError.defaultMessage ?: "",
                        field = fieldError.field,
                        rejectedValue = fieldError.rejectedValue ?: ""
                    )
                )
                acc
            }
        )
        return buildResponseEntity(apiError)
    }

    @ExceptionHandler(Exception::class)
    fun handlerException(ex: Exception): ResponseEntity<Any> {
        val apiError = ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error.", ex)
        return buildResponseEntity(apiError)
    }

    /**
     * Custom exception handlers
     */
    @ExceptionHandler(NoFileInDatabaseException::class)
    fun handlerConverterEmptyStringException(ex: NoFileInDatabaseException): ResponseEntity<Any> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.message, ex)
        return buildResponseEntity(apiError)
    }

    /**
     * Database module exception handlers
     */
}