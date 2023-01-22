package com.he1extg.converterdata.exception

import java.io.IOException

private const val DATABASE_NO_FILE_EXCEPTION_MESSAGE = "File retrieving fails. Result is null value."

class NoFileInDatabase(
    override val message: String = DATABASE_NO_FILE_EXCEPTION_MESSAGE
) : IOException(message)