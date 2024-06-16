package com.api.factory.auth.dto.authorization

import com.api.factory.auth.models.users.UserEntity

data class AuthInput(
    val userEntity: UserEntity,
    val accessToken: String,
    val refreshToken: String,
)
