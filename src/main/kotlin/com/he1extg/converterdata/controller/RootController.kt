package com.he1extg.converterdata.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.time.LocalDateTime

@RestController
class RootController {

    @GetMapping
    fun root(): String {
        val apiUri = MvcUriComponentsBuilder.fromController(
            ConverterFileController::class.java
        ).toUriString()
        return """
            <p>Server datetime: ${LocalDateTime.now()}</p>
            <p>"Converter: PDF to MP3" - Data</p>
            <p>Get link: <a href=$apiUri>$apiUri</a> for more details</p>
        """.trimIndent()
    }
}