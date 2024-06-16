package com.api.factory.auth.utils

import com.api.factory.auth.errors.ValidationError
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.security.Key

@Component
class JwtUtils {
    @Qualifier("JwtKey")
    @Autowired
    private val secretKey: Key? = null

    fun getBody(token: String): Claims = getJwsClaims(token).body

    fun getSubject(token: String): String = getJwsClaims(token).body.subject

    @Throws(ValidationError::class)
    fun getJwsClaims(token: String): Jws<Claims> {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
        } catch (error: ValidationError) {
            throw ValidationError("Ошибка валидации токена")
        }
    }
}
