package com.api.factory.auth.api.http

import com.api.factory.auth.dto.login.LoginInput
import com.api.factory.auth.dto.users.UserTokenOutput
import com.api.factory.auth.services.security.auth.IAuthService
import com.api.factory.auth.utils.JwtUtils
import com.github.michaelbull.result.getOrThrow
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import com.api.factory.auth.errors.AuthError
import com.fasterxml.jackson.databind.ObjectMapper

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: IAuthService,
    private val jwtUtils: JwtUtils,
    private val objectMapper: ObjectMapper
) {
    @PostMapping("login")
    fun loginfirst(
        @RequestBody body: LoginInput,
        response: HttpServletResponse,
    ): ResponseEntity<UserTokenOutput> {
        return ok(authService.authenticateFirst(body, response).getOrThrow())
    }

    @PostMapping("logout")
    fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): ResponseEntity<Nothing> {
        request.getHeader(HttpHeaders.AUTHORIZATION)?.let {
            authService.logoutUser(it.substring(7), response)
            request.session.invalidate()
            return ok().build()
        }?: throw AuthError()
    }

    @GetMapping("me")
    fun test(): ResponseEntity<Any> {
        println( SecurityContextHolder.getContext().authentication.authorities)
        return ok(jwtUtils.getBody(SecurityContextHolder.getContext().authentication.credentials.toString()))
    }
}
