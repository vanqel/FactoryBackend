package com.api.factory.auth.dto.users

data class UserChangePasswordInput(
    val username: String,
    val newPassword: String,
)
