package com.api.factory.auth.services.token

import com.api.factory.auth.dto.token.TokenOutput
import com.api.factory.auth.dto.token.TokenValidationOutput
import com.api.factory.extensions.Result
import jakarta.servlet.http.HttpServletResponse

interface ITokenService {
    fun validate(token: String): Result<TokenValidationOutput>

    fun refresh(
        response: HttpServletResponse,
        token: String,
    ): Result<TokenOutput>
}
