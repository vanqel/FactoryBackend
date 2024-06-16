package com.api.factory.auth.dto.users

data class UserUpdateInput(
    val id: Long,
    val newName: String? = null,
    val newDepartment: Long?,
)
