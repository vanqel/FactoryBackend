package com.api.factory.auth.dto.login

import com.api.factory.auth.dto.users.UserOutput

data class LoginOutput(
    val userDto: UserOutput,
    val accessToken: String,
    val refreshToken: String,
)
