package com.api.factory.auth.models.users.table

import com.api.factory.auth.models.roles.table.RoleTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object UsersRolesTable : Table(name = "users_roles") {
    val user =
        reference(
            "user_id",
            UserTable,
            onDelete = ReferenceOption.CASCADE,
        )

    val role = reference("role_id", RoleTable)

    override val primaryKey = PrimaryKey(user, role, name = "USER_ROLE_PK")
}

