package com.he1extg.converterdata.entity

import com.he1extg.converterdata.dto.user.UserDTO
import com.he1extg.converterdata.exception.EntityToDtoException
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany

@Entity
class ConverterUser(
    val username: String,
    val password: String,
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val credentialsNonExpired: Boolean,
    val enabled: Boolean,
    @OneToMany(mappedBy = "converterUser", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val converterFiles: List<ConverterFile> = listOf()
) : BaseEntity<Long>() {

    fun toUserDTO(): UserDTO {
        return id?.let {
            UserDTO(it, username, emptyList())
        } ?: throw EntityToDtoException("Can not convert ConverterUser entity to UserDTO: 'id' is null.")
    }
}