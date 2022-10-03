package com.he1extg.converterdata.dto

import com.he1extg.converterdata.entity.ConverterFile

class ConverterFileDtoFactory {
    companion object {

        fun getFileList(converterFile: ConverterFile): ConverterFileDto {
            return ConverterFileDto(fileName = converterFile.fileName, id = converterFile.id)
        }
    }
}