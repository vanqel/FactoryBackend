package com.api.factory.auth.filter

import com.api.factory.auth.config.SecurityProperties
import com.api.factory.auth.services.security.auth.AuthProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException

class JWTAuthorizationFilter(
    authManager: AuthenticationManager,
    private val authProvider: AuthProvider,
) : BasicAuthenticationFilter(authManager) {
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain,
    ) {
        val header = req.getHeader(SecurityProperties.HEADER_STRING)
        val cookie: Cookie? = req.cookies?.firstOrNull { it.name.equals(SecurityProperties.ACCESS_COOKIE_STRING) }

        if (cookie == null && (header == null || header.startsWith(SecurityProperties.TOKEN_PREFIX_ACCESS).not())) {
            chain.doFilter(req, res)
            return
        }

        header?.let {
            SecurityContextHolder.getContext().authentication =
                authProvider.getAuthentication(header.replace(SecurityProperties.TOKEN_PREFIX_ACCESS, ""))
            chain.doFilter(req, res)
            return
        }

        cookie?.let {
            SecurityContextHolder.getContext().authentication =
                authProvider.getAuthentication(it.value)
            chain.doFilter(req, res)
            return
        }

        logger.info("No auth")
    }

    companion object : Logging
}
