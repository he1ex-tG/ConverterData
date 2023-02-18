package com.he1extg.converterdata.service

import com.he1extg.converterdata.dao.file.TimestampDAO
import com.he1extg.converterdata.entity.ConverterFile
import com.he1extg.converterdata.dto.file.ContentDTO
import com.he1extg.converterdata.dto.file.FilenameDTO
import com.he1extg.converterdata.exception.NoFileInDatabaseException
import com.he1extg.converterdata.repository.ConverterFileRepository
import com.he1extg.converterdata.repository.ConverterUserRepository
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(DataServiceConfig::class)
class DataServiceImpl(
    config: DataServiceConfig,
    private val converterFileRepository: ConverterFileRepository,
    private val converterUserRepository: ConverterUserRepository
) : DataService {

    private val maxFilesToStore = config.maxFilesToStore.toInt()

    override fun getFileList(userId: Long): List<FilenameDTO> {
        val fileList = converterFileRepository.getFileListWithIdAndFilenameByConverterUserId(userId)
        return fileList.map {
            it.toFilenameDTO()
        }
    }

    override fun getFile(fileId: Long): ContentDTO {
        val fileContent = converterFileRepository.getFileContentById(fileId).orElseThrow {
            NoFileInDatabaseException("File extraction from database encountered with issue. File with id = $fileId not found.")
        }
        return fileContent.toContentDTO()
    }

    /**
     * Control amount of stored files by user.
     * TODO("Need to rework")
     */
    private fun ConverterFileRepository.maxFilesControl(userId: Long, amount: Int): Boolean {
        val converterFiles = this.getFileListWithIdAndTimestampByConverterUserId(userId)
        if (converterFiles.size > amount) {
            val myTimestampComparator = Comparator<TimestampDAO> { a, b ->
                a.timestamp.compareTo(b.timestamp)
            }
            val id = converterFiles.minOfWith(myTimestampComparator) { it }.id
            this.deleteById(id)
            return true
        }
        return false
    }

    override fun setFile(userId: Long, filename: String, content: ByteArray) {
        val proxyUser = converterUserRepository.getReferenceById(userId)
        val newFile = ConverterFile(
            filename = filename,
            content = content,
            converterUser = proxyUser
        )
        converterFileRepository.save(newFile)
        converterFileRepository.maxFilesControl(userId, maxFilesToStore)
    }
}