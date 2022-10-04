package com.he1extg.converterdata.controller

import com.he1extg.converterdata.dto.ConverterFileDto
import com.he1extg.converterdata.dto.ConverterFileDtoFactory
import com.he1extg.converterdata.service.ConverterFileService
import org.springframework.beans.factory.annotation.Autowired
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
    fun getFileList(@RequestParam user: String): ResponseEntity<List<ConverterFileDto>> {
        val fileList = converterFileService.getFileList(user)
        return if (fileList.isNotEmpty()) {
            ResponseEntity
                .ok()
                .body(fileList.map { ConverterFileDtoFactory.getFileList(it) })
        }
        else {
            ResponseEntity
                .noContent()
                .build()
        }
    }

    @GetMapping("/{id}")
    fun getFile(@PathVariable id: Long): ResponseEntity<ConverterFileDto> {
        return try {
            val file = converterFileService.getFile(id)
            ResponseEntity
                .ok()
                .body(ConverterFileDtoFactory.getFile(file))
        }
        catch (e: Exception) {
            ResponseEntity
                .noContent()
                .build()
        }
    }

    @PostMapping
    fun setFile(@RequestParam("user") user: String, @RequestParam("file") file: MultipartFile): ResponseEntity<Unit> {
        return if (user.isEmpty() || file.isEmpty || file.originalFilename.isNullOrBlank()) {
            ResponseEntity
                .badRequest()
                .build()
        }
        else {
            converterFileService.setFile(user, file.originalFilename!!, file.bytes)
            ResponseEntity
                .ok()
                .build()
        }
    }
}