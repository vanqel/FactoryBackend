package com.api.factory.auth.models.roles.table

import com.api.factory.config.ExtendedLongIdTable
import org.jetbrains.exposed.sql.Column

object RoleTable : ExtendedLongIdTable(name = "roles") {
    val roleEnum: Column<RolesEnum> = enumerationByName(name = "role_name", length = 100) // readonly
}
