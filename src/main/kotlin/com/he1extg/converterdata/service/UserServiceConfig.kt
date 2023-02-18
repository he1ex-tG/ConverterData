package com.he1extg.converterdata.service

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserServiceConfig {

    @Bean
    fun addUsers(userService: UserService): CommandLineRunner {
        return CommandLineRunner {
            userService.addUser("guest", "")
        }
    }
}