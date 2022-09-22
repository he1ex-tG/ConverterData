package com.he1extg.converterdata.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    fun enableOAuth2(http: HttpSecurity): SecurityFilterChain {
        http.invoke {
            csrf {
                disable()
            }
            authorizeRequests {
                authorize(anyRequest, permitAll)
            }
            anonymous {
                principal = "anonymous"
            }
            oauth2Login {  }
        }
        return http.build()
    }
}