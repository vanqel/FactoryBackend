package com.api.factory.auth.services.security.auth

import com.api.factory.auth.config.SecurityProperties
import com.api.factory.auth.dto.authorization.AuthInput
import com.api.factory.auth.dto.login.LoginInput
import com.api.factory.auth.dto.token.TokenOutput
import com.api.factory.auth.dto.users.UserOutput
import com.api.factory.auth.dto.users.UserTokenOutput
import com.api.factory.auth.errors.AuthError
import com.api.factory.auth.repository.auth.IAuthRepository
import com.api.factory.auth.repository.user.IUsersRepository
import com.api.factory.auth.services.security.app.AppAuthenticationManager
import com.api.factory.auth.services.token.TokenProvider
import com.api.factory.auth.utils.JwtUtils
import com.api.factory.extensions.Result
import com.api.factory.extensions.error
import com.api.factory.extensions.ok
import com.api.factory.extensions.setTokens
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val userRepository: IUsersRepository,
    private val appAuthenticationManager: AppAuthenticationManager,
    private val tokenProvider: TokenProvider,
    private val authRepository: IAuthRepository,
    private val jwtUtils: JwtUtils,
) : IAuthService {
    override fun authenticateFirst(
        body: LoginInput,
        response: HttpServletResponse,
    ): Result<UserTokenOutput> {
        var authentication =
            try {
                appAuthenticationManager.authenticateFirst(body)
            } catch (e: BadCredentialsException) {
                return Result.error(AuthError())
            }

        val user = userRepository.findUserByUsername(authentication.name) ?: return Result.error(AuthError())

        val accessToken = tokenProvider.createAccessToken(authentication, user.toDTO())
        val refreshToken = tokenProvider.createRefreshToken(authentication)

        authentication =
            try {
                appAuthenticationManager.authenticateFully(accessToken)
            } catch (e: BadCredentialsException) {
                return Result.error(AuthError())
            }

        SecurityContextHolder.getContext().authentication = authentication

        response.setTokens(accessToken, refreshToken)

        return userRepository.findUserByUsername(authentication.name)?.let {
            authRepository.save(AuthInput(it, accessToken, refreshToken))
            Result.ok(UserTokenOutput(UserOutput(it), TokenOutput(accessToken, refreshToken)))
        }?:  Result.error(AuthError())
    }

    override fun logoutUser(
        token: String,
        response: HttpServletResponse,
    ) {
        response.setHeader(SecurityProperties.HEADER_STRING, "")
        authRepository.deleteByAccessToken(token)
        SecurityContextHolder.getContext().authentication = null
    }
}
