package com.api.factory.auth.dto.authorization

data class AuthOutput(
    val uid: Long,
    val accessToken: String,
    val refreshToken: String,
)
