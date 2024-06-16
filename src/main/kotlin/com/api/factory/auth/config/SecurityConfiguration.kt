package com.api.factory.auth.config

import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.crypto.SecretKey

@Configuration
@EnableConfigurationProperties(
    SecurityProperties::class,
)
class SecurityConfiguration(
    val securityProperties: SecurityProperties,
) {
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder(securityProperties.strength)

    @Bean
    fun secretKey(securityProperties: SecurityProperties): SecretKey {
        return Keys.hmacShaKeyFor(securityProperties.secret.toByteArray())
    }
}
