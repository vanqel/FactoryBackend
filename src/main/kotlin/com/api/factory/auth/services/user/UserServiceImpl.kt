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


    fun getRole() = SecurityContextHolder.getContext().authentication.authorities.map {
        it.authority
    }.first()

    fun validate() {
        if ((getRole() !in listOf(RolesEnum.ADMIN.name, RolesEnum.DIMK.name))
        ) {
            throw AuthError()
        }
    }

    fun validateB(r: String) {
        if ((r in listOf(RolesEnum.ADMIN.name, RolesEnum.DIMK.name))
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

        if (body.role == RolesEnum.DIMK && getRole() != RolesEnum.ADMIN.name) {
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
        userId: Long,
    ): Result<UserBlockOutput> {

        validate()

        val user = usersRepository.findUserById(userId)!!

        if (user.roles.first().name == RolesEnum.ADMIN) {
            return Result.error(AuthError())
        }

        if (user.roles.first().name == RolesEnum.DIMK && getRole() != RolesEnum.ADMIN.name) {
            return Result.error(AuthError())
        }

        return try {
            Result.ok(usersRepository.blockUser(userId))
        } catch (e: Exception) {
            Result.error(ValidationError("Ошибка удаления пользователя"))
        }
    }

    override fun updatePassword(body: UserChangePasswordInput): Result<UserChangePasswordOutput> {
        validate()

        return if (usersRepository.existUserByUsername(body.username))
            Result.ok(usersRepository.updatePassword(body))
        else Result.error(ValidationError("Такого пользователя не существует"))
    }

    override fun getUsers(): Result<List<UserOutput?>> {
        return Result.ok(usersRepository.all().filter { !it.isBlocked })
    }

    override fun getUser(id: Long): Result<UserOutput?> {
        return Result.ok(usersRepository.byId(id))
    }
}
