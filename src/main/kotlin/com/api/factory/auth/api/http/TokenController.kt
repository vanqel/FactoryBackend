package com.api.factory.auth.api.http

import com.api.factory.auth.dto.token.RefreshTokenInput
import com.api.factory.auth.dto.token.TokenOutput
import com.api.factory.auth.dto.token.TokenValidationOutput
import com.api.factory.auth.services.token.ITokenService
import com.github.michaelbull.result.getOrThrow
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/token")
class TokenController(
    private val tokenService: ITokenService,
) {
    @GetMapping("validate")
    fun validate(
        @RequestParam(value = "token") token: String,
    ): ResponseEntity<TokenValidationOutput> {
        return ok(tokenService.validate(token).getOrThrow())
    }

    @GetMapping("validateUser")
    fun validateHeader(
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String,
    ): ResponseEntity<TokenValidationOutput> {
        return ok(tokenService.validate(token).getOrThrow())
    }

    @PostMapping("refresh")
    fun register(
        @RequestBody body: RefreshTokenInput,
        response: HttpServletResponse,
    ): ResponseEntity<TokenOutput> {
        return ok(tokenService.refresh(response, body.token).getOrThrow())
    }
}
