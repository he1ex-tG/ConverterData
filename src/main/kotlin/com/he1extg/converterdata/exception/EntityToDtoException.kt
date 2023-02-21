package com.he1extg.converterdata.exception

import java.io.IOException

private const val ENTITY_TO_DTO_EXCEPTION_MESSAGE = "Can not convert entity to DTO."

class EntityToDtoException(
    override val message: String = ENTITY_TO_DTO_EXCEPTION_MESSAGE
) : IOException(message)