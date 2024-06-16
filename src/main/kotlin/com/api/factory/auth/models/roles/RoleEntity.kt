@file:Suppress("unused")

package com.api.factory.auth.models.roles

import com.api.factory.auth.models.roles.table.RoleTable
import com.api.factory.auth.models.users.UserEntity
import com.api.factory.auth.models.users.table.UsersRolesTable
import com.api.factory.config.ExtendedLongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RoleEntity(id: EntityID<Long>) : ExtendedLongEntity(id, RoleTable) {
    companion object : LongEntityClass<RoleEntity>(RoleTable)

    var name by RoleTable.roleEnum

    var users by UserEntity via UsersRolesTable
}
