package com.api.factory.auth.services.security.auth

import com.api.factory.auth.dto.login.LoginInput
import com.api.factory.auth.dto.users.UserTokenOutput
import com.api.factory.extensions.Result
import jakarta.servlet.http.HttpServletResponse

interface IAuthService {
    fun authenticateFirst(
        body: LoginInput,
        response: HttpServletResponse,
    ): Result<UserTokenOutput>

    fun logoutUser(
        token: String,
        response: HttpServletResponse,
    )
}
