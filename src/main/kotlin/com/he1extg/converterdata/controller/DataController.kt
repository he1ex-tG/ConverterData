package com.he1extg.converterdata.controller

import com.he1extg.converterdata.dto.FilenameBytearrayDTO
import com.he1extg.converterdata.dto.IdFilenameDTO
import com.he1extg.converterdata.dto.FileUploadDTO
import com.he1extg.converterdata.service.DataService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1")
class DataController(
    private val dataService: DataService
) {
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

    @GetMapping("/files")
    fun getFileList(@RequestParam(required = false) username: String?): ResponseEntity<List<IdFilenameDTO>> {
        val fileList = dataService.getFileList(username)
        return ResponseEntity
            .ok()
            .body(fileList)
    }

    @GetMapping("/files/{id}")
    fun getFile(@PathVariable id: Long): ResponseEntity<FilenameBytearrayDTO> {
        val file = dataService.getFile(id)
        return ResponseEntity
            .ok()
            .body(file)
    }

    @PostMapping("/files")
    fun setFile(@Valid @RequestBody fileUploadDTO: FileUploadDTO): ResponseEntity<Unit> {
        dataService.setFile(fileUploadDTO.username, fileUploadDTO.filename, fileUploadDTO.content)
        return ResponseEntity
            .ok()
            .build()
    }
}