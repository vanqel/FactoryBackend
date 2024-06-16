package com.api.factory.auth.repository.user

import com.api.factory.auth.dto.users.*
import com.api.factory.auth.models.roles.RoleEntity
import com.api.factory.auth.models.users.UserEntity
import com.api.factory.auth.models.users.UsersDepartmentTable
import com.api.factory.auth.models.users.table.UserTable
import com.api.factory.auth.models.users.table.UsersRolesTable
import com.api.factory.extensions.exists
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class UsersRepository(
    val passwordEncoder: PasswordEncoder,
) : IUsersRepository {
    override fun save(body: UserCreateInput): UserOutput {
        val newUserEntity =
            transaction {
                UserEntity.new {
                    username = body.username
                    password = passwordEncoder.encode(body.password)
                    name = body.name
                }.let { u ->

                    UsersDepartmentTable.insert {
                        it[user] = u.id
                        it[department] = body.department
                    }
                    UsersRolesTable.insert {
                        it[user] = u.id
                        it[role] = RoleEntity.findById(body.role.id)!!.id
                    }
                    commit()
                    u
                }
            }




        return newUserEntity.toDTO()
    }

    override fun existUserByUsername(username: String): Boolean =
        UserEntity.find {
            UserTable.username eq username
        }.exists()

    override fun existUserById(id: Long): Boolean =
        UserEntity.find {
            UserTable.id eq id
        }.exists()

    override fun findUserByUsername(username: String): UserEntity? =
        UserEntity.find {
            UserTable.username eq username
        }.firstOrNull()

    override fun findUserById(id: Long): UserEntity? =
        UserEntity.find {
            UserTable.id eq id
        }.firstOrNull()

    override fun compareIdAndUsername(
        username: String,
        id: Long,
    ): Boolean =
        findUserById(id)
            ?.let {
                it.username == username
            } ?: false

    override fun updateUser(body: UserUpdateInput): UserOutput {
        UserTable.update({ UserTable.id eq body.id }) {
            it[name] = body.newName!!
        }
        body.newDepartment?.let { d ->
            UsersDepartmentTable.update({ UsersDepartmentTable.user eq body.id }) {
                it[department] = d
            }
        }
        return UserEntity.findById(body.id)!!.toDTO()
    }

    override fun updatePassword(body: UserChangePasswordInput): UserChangePasswordOutput {
        UserTable.update({ UserTable.username eq body.username }) {
            it[password] = passwordEncoder.encode(body.newPassword)
        }
        return UserChangePasswordOutput(true)
    }

    override fun blockUser(id: Long): UserBlockOutput {
        val status = UserEntity.findById(id)!!.isBlocked
        UserTable.update({ UserTable.id eq id }) {
            it[isBlocked] = !status
        }
        return UserBlockOutput(!status)
    }

    override fun all(): List<UserOutput> {
        return UserEntity.all().toList().map { it.toDTO() }
    }
    override fun byId(id: Long): UserOutput? {
        return findUserById(id)?.toDTO()
    }
}
