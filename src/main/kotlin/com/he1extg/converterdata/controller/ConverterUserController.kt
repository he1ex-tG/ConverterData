package com.he1extg.converterdata.controller

import com.he1extg.converterdata.service.ConverterUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class ConverterUserController {

    @Autowired
    lateinit var converterUserService: ConverterUserService

    @GetMapping
    fun getUserList(): List<String> {
        return converterUserService.getUserList()
    }
}