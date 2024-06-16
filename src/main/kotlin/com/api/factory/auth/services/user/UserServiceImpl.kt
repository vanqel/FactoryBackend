package com.api.factory.auth.services.user

import com.api.factory.auth.dto.users.*
import com.api.factory.auth.errors.AuthError
import com.api.factory.auth.errors.ValidationError
import com.api.factory.auth.models.roles.table.RolesEnum
import com.api.factory.auth.repository.user.UsersRepository
import com.api.factory.extensions.*
import com.api.factory.extensions.Result
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    val usersRepository: UsersRepository,
) : IUserService {

    fun validate() {
        val roles = SecurityContextHolder.getContext().authentication.authorities.map {
            it.authority
        }.first()

        if ((roles !in listOf(RolesEnum.ADMIN.name, RolesEnum.DIMK.name))
        ) {
            throw AuthError()
        }
    }

    override fun registerUser(body: UserCreateInput): Result<UserOutput> {

        validate()

        if (
            body.role == RolesEnum.ADMIN
        ) {
            return Result.error(AuthError())
        }

        val user: UserOutput = usersRepository.save(body)

        return Result.ok(user)
    }

    override fun updateUser(body: UserUpdateInput): Result<UserOutput> {
        validate()

        val user: UserOutput = usersRepository.updateUser(body)

        return Result.ok(user)
    }

    override fun blockUser(
        username: String?,
        userId: Long?,
    ): Result<UserBlockOutput> {

        validate()

        if (username.isNull() && userId.isNull()) {
            return Result.error(ValidationError("Такого пользователя не существует"))
        }

        val result: UserBlockOutput? =
            if (username.isNotNull() &&
                userId.isNotNull() &&
                (usersRepository.compareIdAndUsername(username!!, userId!!)) // Переделать
            ) {
                usersRepository.blockUser(userId)
            } else {
                if (username.isNull()) {
                    usersRepository.findUserById(userId!!)?.let {
                        usersRepository.blockUser(it.id.value)
                    }
                } else {
                    usersRepository.findUserByUsername(username!!)?.let {
                        usersRepository.blockUser(it.id.value)
                    }
                }
            }
        return result?.let {
            Result.ok(it)
        } ?: Result.error(ValidationError("Такого пользователя не существует"))
    }

    override fun updatePassword(body: UserChangePasswordInput): Result<UserChangePasswordOutput> {
        validate()

        return if (usersRepository.existUserByUsername(body.username))
            Result.ok(usersRepository.updatePassword(body))
        else Result.error(ValidationError("Такого пользователя не существует"))
    }

    override fun getUsers(): Result<List<UserOutput?>> {
        return Result.ok(usersRepository.all())
    }

    override fun getUser(id: Long): Result<UserOutput?> {
        return Result.ok(usersRepository.byId(id))
    }
}
