package com.api.factory.auth.services.token

import com.api.factory.auth.config.SecurityProperties
import com.api.factory.auth.dto.authorization.AuthInput
import com.api.factory.auth.dto.token.TokenOutput
import com.api.factory.auth.dto.token.TokenValidationOutput
import com.api.factory.auth.errors.ValidationError
import com.api.factory.auth.models.users.UserEntity
import com.api.factory.auth.repository.auth.IAuthRepository
import com.api.factory.extensions.Result
import com.api.factory.extensions.error
import com.api.factory.extensions.ok
import com.api.factory.extensions.setTokens
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val authRepository: IAuthRepository,
    private val tokenProvider: TokenProviderImpl,
) : ITokenService {
    override fun validate(token: String): Result<TokenValidationOutput> {
        val tokenVal =
            if (token.startsWith(SecurityProperties.TOKEN_PREFIX_ACCESS)) {
                token.replace(SecurityProperties.TOKEN_PREFIX_ACCESS, "")
            } else {
                token
            }

        return Result.ok(TokenValidationOutput(true))
    }

    override fun refresh(
        response: HttpServletResponse,
        token: String,
    ): Result<TokenOutput> {

        authRepository.findAuthByRefreshToken(token)?.let {
            val accessToken = tokenProvider.updatedAccessToken(it.access)
            val refreshToken = tokenProvider.updatedRefreshToken(it.refresh)

            response.setTokens(accessToken, refreshToken)

            authRepository.save(AuthInput(UserEntity(it.user), accessToken, refreshToken))

            return Result.ok(TokenOutput(accessToken, refreshToken))
        }

        return Result.error(
            ValidationError(
                "Ошибка валидации токенов",
                mapOf("Обновление пары токенов" to "Произошла ошибка"),
            ),
        )
    }
}
