package com.he1extg.converterdata.service

import com.he1extg.converterdata.entity.ConverterFile
import com.he1extg.converterdata.dto.FilenameBytearrayDTO
import com.he1extg.converterdata.dto.IdFilenameDTO
import com.he1extg.converterdata.dto.IdTimestampDTO
import com.he1extg.converterdata.exception.NoFileInDatabaseException
import com.he1extg.converterdata.repository.ConverterFileRepository
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(DataServiceConfig::class)
class DataServiceImpl(
    config: DataServiceConfig,
    private val converterFileRepository: ConverterFileRepository
) : DataService {

    private val maxFilesToStore = config.maxFilesToStore.toInt()

    override fun getFileList(username: String?): List<IdFilenameDTO> {
        return username?.let {
            converterFileRepository.getConverterFileListByConverterUser(it)
        }
            ?: emptyList()
    }

    override fun getFile(converterFileId: Long): FilenameBytearrayDTO {
        val converterFile = converterFileRepository.getConverterFileById(converterFileId)
        return converterFile.orElseThrow {
            NoFileInDatabaseException("File extraction from database encountered with issue. File with id = $converterFileId not found.")
        }
    }

    /**
     * Control amount of stored files by user.
     */
    private fun ConverterFileRepository.maxFilesControl(converterUser: String, amount: Int): Boolean {
        val converterFiles = this.getConverterFileTimestampByConverterUser(converterUser)
        if (converterFiles.size > amount) {
            val myTimestampComparator = Comparator<IdTimestampDTO> { a, b ->
                a.timestamp.compareTo(b.timestamp)
            }
            val id = converterFiles.minOfWith(myTimestampComparator) { it }.id
            id?.let {
                this.deleteById(id)
                return true
            }
        }
        return false
    }

    override fun setFile(userName: String, filename: String, fileByteArray: ByteArray) {
        val newFile = ConverterFile(filename, fileByteArray, userName)
        converterFileRepository.save(newFile)
        converterFileRepository.maxFilesControl(userName, maxFilesToStore)
    }
}