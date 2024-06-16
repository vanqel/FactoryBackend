package com.api.factory.auth.services.token

import com.api.factory.auth.config.SecurityProperties
import com.api.factory.auth.dto.users.UserOutput
import com.api.factory.auth.filter.TokenProps
import com.api.factory.auth.utils.JwtUtils
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.time.Duration
import java.time.Instant
import java.util.Date

@Component
class TokenProviderImpl(
    private val securityProperties: SecurityProperties,
    private val jwtUtils: JwtUtils,
) : TokenProvider {
    companion object : Logging

    @Qualifier("JwtKey")
    @Autowired
    private val signInKey: Key? = null

    override fun createAccessToken(authentication: Authentication, user: UserOutput): String {
        val authClaims =
            authentication.authorities.map {
                it.toString()
            }

        val expiration =
            Date.from(
                Instant.now().plus(Duration.ofMinutes(securityProperties.expirationTime)),
            )
        val data = mutableMapOf<String, Any?>()
        data[TokenProps.TYPE] = TokenProps.ACCESS_TOKEN
        data["auth"] = authClaims
        data["user"] = user
        return Jwts.builder()
            .setSubject(authentication.name)
            .addClaims(data)
            .setExpiration(expiration)
            .signWith(signInKey, SignatureAlgorithm.HS256)
            .compact()
    }

    override fun createRefreshToken(authentication: Authentication): String {
        val expiration =
            Date.from(
                Instant.now().plus(Duration.ofMinutes(securityProperties.expirationTimeRefresh)),
            )

        return Jwts.builder()
            .setSubject(authentication.name)
            .claim(TokenProps.TYPE, TokenProps.REFRESH_TOKEN)
            .setExpiration(expiration)
            .signWith(signInKey, SignatureAlgorithm.HS256)
            .compact()
    }

    override fun updatedAccessToken(token: String): String {
        val claims = jwtUtils.getBody(token)
        val expiration =
            Date.from(
                Instant.now().plus(Duration.ofMinutes(securityProperties.expirationTime)),
            )
        return Jwts.builder()
            .setSubject(claims.subject)
            .addClaims(claims)
            .setExpiration(expiration)
            .signWith(signInKey, SignatureAlgorithm.HS256)
            .compact()
    }

    override fun updatedRefreshToken(token: String): String {
        val claims = jwtUtils.getBody(token)
        val expiration =
            Date.from(
                Instant.now().plus(Duration.ofMinutes(securityProperties.expirationTimeRefresh)),
            )
        return Jwts.builder()
            .setSubject(claims.subject)
            .addClaims(claims)
            .setExpiration(expiration)
            .signWith(signInKey, SignatureAlgorithm.HS256)
            .compact()
    }
}
