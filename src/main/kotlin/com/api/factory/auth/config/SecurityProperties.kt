package com.api.factory.auth.config

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "jwt-security")
class SecurityProperties
    @ConstructorBinding
    constructor(
        @field:NotBlank
        @field:Size(min = 64)
        val secret: String,
        @field:Positive
        val expirationTime: Long,
        @field:Positive
        val expirationTimeRefresh: Long,
        @field:Positive
        val strength: Int = 10,
    ) {
        companion object : Logging {
            const val TOKEN_PREFIX_ACCESS = "Bearer "
            const val HEADER_STRING = "Authorization"
            const val REFRESH_HEADER_STRING = "Refresh"
            const val ACCESS_COOKIE_STRING = "Access"
            const val REFRESH_COOKIE_STRING = "Refresh"
        }
    }
