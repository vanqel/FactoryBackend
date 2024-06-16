package com.api.factory.auth.repository.user
import com.api.factory.auth.dto.users.*
import com.api.factory.auth.models.users.UserEntity

interface IUsersRepository {
    fun save(body: UserCreateInput): UserOutput
    fun existUserByUsername(username: String): Boolean
    fun existUserById(id: Long): Boolean
    fun findUserByUsername(username: String): UserEntity?
    fun findUserById(id: Long): UserEntity?
    fun compareIdAndUsername(username: String, id: Long): Boolean
    fun updateUser(body: UserUpdateInput): UserOutput
    fun updatePassword(body: UserChangePasswordInput): UserChangePasswordOutput
    fun blockUser(id: Long): UserBlockOutput
    fun all(): List<UserOutput>
    fun byId(id: Long): UserOutput?
}
