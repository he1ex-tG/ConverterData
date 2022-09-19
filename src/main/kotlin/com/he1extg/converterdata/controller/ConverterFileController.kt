package com.he1extg.converterdata.controller

import com.he1extg.converterdata.service.ConverterFileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/files")
class ConverterFileController {

    @Autowired
    lateinit var converterFileService: ConverterFileService

    @GetMapping
    fun getFileList(): List<String> {
        return converterFileService.getFileList()
    }
}