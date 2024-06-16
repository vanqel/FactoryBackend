package com.api.factory.auth.dto.users

import com.api.factory.auth.models.roles.table.RolesEnum

data class UserCreateInput(
    val username: String,
    val password: String,
    val name: String,
    val role: RolesEnum,
    val department: Long,
)
