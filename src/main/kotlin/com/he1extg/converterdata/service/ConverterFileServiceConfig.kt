package com.he1extg.converterdata.service

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "storage")
class ConverterFileServiceConfig {
    lateinit var maxFilesToStore: String
}