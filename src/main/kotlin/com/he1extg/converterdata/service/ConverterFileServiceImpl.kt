package com.he1extg.converterdata.service

import com.he1extg.converterdata.entity.ConverterFile
import com.he1extg.converterdata.repository.ConverterFileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.io.FileNotFoundException

@Service
@EnableConfigurationProperties(ConverterFileServiceConfig::class)
class ConverterFileServiceImpl(
    config: ConverterFileServiceConfig,
) : ConverterFileService {

    @Autowired
    lateinit var converterFileRepository: ConverterFileRepository

    private val maxFilesToStore = config.maxFilesToStore.toInt()

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

    /**
     * Control amount of stored files by user.
     */
    private fun ConverterFileRepository.maxFilesControl(converterUser: String, amount: Int): Boolean {
        val converterFiles = this.findAllByConverterUser(converterUser)
        if (converterFiles.size > amount) {
            val myTimestampComparator = Comparator<ConverterFile> { a, b -> a.timestamp.compareTo(b.timestamp) }
            val id = converterFiles.minOfWith(myTimestampComparator) { it }.id
            id?.let {
                this.deleteById(id)
                return true
            }
        }
        return false
    }

    override fun setFile(userName: String, fileName: String, fileByteArray: ByteArray) {
        val newFile = ConverterFile(fileName, fileByteArray, userName)
        converterFileRepository.save(newFile)
        converterFileRepository.maxFilesControl(userName, maxFilesToStore)
    }
}