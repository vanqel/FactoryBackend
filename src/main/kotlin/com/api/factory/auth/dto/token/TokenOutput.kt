package com.api.factory.auth.dto.token

data class TokenOutput(
    val access: String,
    val refresh: String,
)
