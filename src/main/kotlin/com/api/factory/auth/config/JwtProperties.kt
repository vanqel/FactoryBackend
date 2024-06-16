package com.api.factory.auth.config

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.Key

@Configuration
class JwtProperties(private val securityProperties: SecurityProperties) {
    @Bean(name = ["JwtKey"])
    fun singInKey(): Key? = Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityProperties.secret))
}
