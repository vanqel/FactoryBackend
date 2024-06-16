package com.api.factory.auth.models.users

import com.api.factory.auth.dto.users.UserOutput
import com.api.factory.auth.models.department.DepartmentEntity
import com.api.factory.auth.models.roles.RoleEntity
import com.api.factory.auth.models.users.table.UserTable
import com.api.factory.auth.models.users.table.UsersRolesTable
import com.api.factory.config.ExtendedLongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Long>) : ExtendedLongEntity(id, UserTable) {
    companion object : LongEntityClass<UserEntity>(UserTable)

    var username by UserTable.username
    var password by UserTable.password
    var name by UserTable.name
    var department by DepartmentEntity via UsersDepartmentTable
    var roles by RoleEntity via UsersRolesTable
    var isBlocked by UserTable.isBlocked

    fun toDTO(): UserOutput {
        return UserOutput(this)
    }
}
