package com.he1extg.converterdata.controller

import com.he1extg.converterdata.dto.UsernamePasswordDTO
import com.he1extg.converterdata.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/users/{username}")
    fun getUser(@PathVariable username: String): ResponseEntity<UsernamePasswordDTO> {
        val user = userService.getUser(username)
        return ResponseEntity
            .ok()
            .body(user)
    }

    @PostMapping("/users")
    fun addUser(@Valid @RequestBody usernamePasswordDTO: UsernamePasswordDTO): ResponseEntity<Unit> {
        userService.addUser(usernamePasswordDTO.username, usernamePasswordDTO.password)
        return ResponseEntity
            .ok()
            .build()
    }
}