package com.he1extg.converterdata.service

import com.he1extg.converterdata.entity.ConverterFile
import com.he1extg.converterdata.repository.ConverterFileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.FileNotFoundException

@Service
class ConverterFileServiceImpl : ConverterFileService {

    @Autowired
    lateinit var converterFileRepository: ConverterFileRepository

    override fun getFileList(userName: String): List<ConverterFile> {
        return converterFileRepository.findAllByConverterUser(userName)
    }

    override fun getFile(fileId: Long): ConverterFile {
        val converterFile = converterFileRepository.findById(fileId)
        return if (converterFile.isPresent) {
            converterFile.get()
        }
        else {
            throw FileNotFoundException(
                "File extraction from database encountered with issue. " +
                        "File with id = $fileId not found."
            )
        }
    }

    override fun setFile(userName: String, fileName: String, fileByteArray: ByteArray) {
        val newFile = ConverterFile(fileName, fileByteArray, userName)
        converterFileRepository.save(newFile)
        //TODO("File count check")
    }
}