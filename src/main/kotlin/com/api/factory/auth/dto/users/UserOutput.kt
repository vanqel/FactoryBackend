package com.api.factory.auth.dto.users

import com.api.factory.auth.dto.department.DepartmentOutput
import com.api.factory.auth.models.roles.table.RolesEnum
import com.api.factory.auth.models.users.UserEntity

class UserOutput(
    val id: Long,
    val name: String,
    val username: String,
    val roles: List<RolesEnum>,
    val department: DepartmentOutput,
    val isBlocked: Boolean,
) {
    constructor(u: UserEntity) : this(
        u.id.value,
        u.name,
        u.username,
        u.roles.map { it.name },
        u.department.first().let { it.toDTO() },
        u.isBlocked,
    )
}
