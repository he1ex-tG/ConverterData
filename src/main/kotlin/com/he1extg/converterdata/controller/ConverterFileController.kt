package com.he1extg.converterdata.controller

import com.he1extg.converterdata.dto.FilenameBytearrayDTO
import com.he1extg.converterdata.dto.IdFilenameDTO
import com.he1extg.converterdata.dto.FileUploadDTO
import com.he1extg.converterdata.service.ConverterFileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1")
class ConverterFileController {

    @Autowired
    lateinit var converterFileService: ConverterFileService

    /**
    TODO Replace boilerplate code
    @GetMapping
    fun root(): String {
        val getFileList = MvcUriComponentsBuilder.fromMethodName(
            this::class.java,
            this::getFileList.name,
            "",
        ).build()
        val getFileListStr = getFileList.scheme + "://" + getFileList.host + ":" + getFileList.port + getFileList.path
        val getFile = MvcUriComponentsBuilder.fromMethodName(
            this::class.java,
            this::getFile.name,
            "",
        ).build()
        val getFileStr = getFile.scheme + "://" + getFile.host + ":" + getFile.port + getFile.path
        val setFile = MvcUriComponentsBuilder.fromMethodName(
            this::class.java,
            this::setFile.name,
            "", "",
        ).build()
        val setFileStr = setFile.scheme + "://" + setFile.host + ":" + setFile.port + setFile.path
        return """
            <p>Server datetime: ${LocalDateTime.now()}</p>
            <p>"Converter: PDF to MP3" - Data</p>
            <p></p>
            <div>Endpoints:</div>
            <div>1. URI - <a href=$getFileListStr>$getFileListStr</a></div>
            <div>Request: Method - GET; Param - user</div>
            <div>Response: Content type - application/json if returned list is not empty; Return type - ResponseEntity with List of IdFilenameDTO</div>
            <div>2. URI - <a href=$getFileStr>$getFileStr</a></div>
            <div>Request: Method - GET; Path variable {id} - id of file in database</div>
            <div>Response: Content type - application/json if file exists in database; Return type - ResponseEntity with FilenameBytearrayDTO</div>
            <div>3. URI - <a href=$setFileStr>$setFileStr</a></div>
            <div>Request: Method - POST; Content type - multipart/form-data; Body params - user: String, file: MultipartFile</div>
            <div>Response: Return type - ResponseEntity with Unit (just status)</div>
         """.trimIndent()
    }*/

    /**
     * TODO Add null username handling - return list of all stored files
     */
    @GetMapping("/files")
    fun getFileList(@RequestParam user: String): ResponseEntity<List<IdFilenameDTO>> {
        if (user.isEmpty()) {
            return ResponseEntity
                .badRequest()
                .build()
        }
        val fileList = converterFileService.getFileList(user)
        return if (fileList.isNotEmpty()) {
            ResponseEntity
                .ok()
                .body(fileList)
        }
        else {
            ResponseEntity
                .noContent()
                .build()
        }
    }

    @GetMapping("/files/{id}")
    fun getFile(@PathVariable id: Long): ResponseEntity<FilenameBytearrayDTO> {
        return try {
            val file = converterFileService.getFile(id)
            ResponseEntity
                .ok()
                .body(file)
        }
        catch (e: Exception) {
            ResponseEntity
                .noContent()
                .build()
        }
    }

    @PostMapping("/files")
    fun setFile(@RequestBody fileUploadDTO: FileUploadDTO?): ResponseEntity<Unit> {
        return if (fileUploadDTO?.content == null || fileUploadDTO.content.isEmpty()) {
            ResponseEntity
                .badRequest()
                .build()
        } else {
            converterFileService.setFile(fileUploadDTO.user, fileUploadDTO.name, fileUploadDTO.content)
            ResponseEntity
                .ok()
                .build()
        }
    }

    @PostMapping("/files/multipart")
    fun setMultipartFile(@RequestParam("user") user: String, @RequestParam("file") file: MultipartFile): ResponseEntity<Unit> {
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