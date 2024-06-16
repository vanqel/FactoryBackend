package com.api.factory.auth.services.token

import com.api.factory.auth.dto.users.UserOutput
import org.springframework.security.core.Authentication

interface TokenProvider {
    fun createAccessToken(authentication: Authentication, user: UserOutput): String

    fun createRefreshToken(authentication: Authentication): String

    fun updatedAccessToken(token: String): String

    fun updatedRefreshToken(token: String): String
}
