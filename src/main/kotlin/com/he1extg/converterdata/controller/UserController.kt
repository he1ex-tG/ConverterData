package com.he1extg.converterdata.controller

import com.he1extg.converterdata.dto.user.AuthenticationDTO
import com.he1extg.converterdata.dto.user.NewUserDTO
import com.he1extg.converterdata.dto.user.UserDTO
import com.he1extg.converterdata.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/users/authentication/{username}")
    fun getUserAuthentication(@PathVariable username: String): ResponseEntity<AuthenticationDTO> {
        val user = userService.getUserAuthentication(username)
        return ResponseEntity
            .ok()
            .body(user)
    }

    @GetMapping("/users/{username}")
    fun getUser(@PathVariable username: String): ResponseEntity<UserDTO> {
        val user = userService.getUser(username)
        return ResponseEntity
            .ok()
            .body(user)
    }

    @PostMapping("/users")
    fun addUser(@Valid @RequestBody newUser: NewUserDTO): ResponseEntity<Unit> {
        userService.addUser(newUser.username, newUser.password)
        return ResponseEntity
            .ok()
            .build()
    }
}