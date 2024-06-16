package com.api.factory.auth.repository.auth

import com.api.factory.auth.dto.authorization.AuthInput
import com.api.factory.auth.dto.authorization.AuthOutput
import com.api.factory.auth.models.authorites.UserLoginEntity

interface IAuthRepository {
    fun save(inputAuth: AuthInput): AuthOutput?
    fun deleteByAccessToken(token: String): Boolean
    fun existAccessToken(token: String): Boolean
    fun existRefreshToken(token: String): Boolean
    fun findAuthByRefreshToken(refreshToken: String): UserLoginEntity?
}
