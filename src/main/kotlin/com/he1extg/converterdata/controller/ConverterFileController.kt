package com.he1extg.converterdata.controller

import com.he1extg.converterdata.service.ConverterFileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/files")
class ConverterFileController {

    @Autowired
    lateinit var converterFileService: ConverterFileService

    @GetMapping
    fun getFileList(): List<String> {
        return converterFileService.getFileList()
    }

    @GetMapping("/{fileName:.+}")
    fun getFile(@PathVariable fileName: String): ResponseEntity<Resource> {
        return try {
            val fileByteArray: ByteArray = converterFileService.getFile(fileName)
            val resource: Resource = object : ByteArrayResource(fileByteArray) {
                override fun getFilename(): String {
                    return fileName
                }
            }
            ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"${resource.filename}\""
            ).body(resource)
        }
        catch (e: Exception) {
            ResponseEntity.noContent().header(
                HttpHeaders.CONTENT_DISPOSITION,
                ""
            ).build()
        }
    }

    @PostMapping
    fun setFile(@RequestParam("file") file: MultipartFile): HttpStatus {
        return if (!file.isEmpty) {
            val setFile = converterFileService.setFile(file.originalFilename ?: "default_file_name", file.bytes)
            if (setFile) {
                HttpStatus.OK
            } else {
                HttpStatus.INTERNAL_SERVER_ERROR
            }
        } else {
            HttpStatus.BAD_REQUEST
        }
    }
}