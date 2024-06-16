package com.api.factory.auth.services.user

import com.api.factory.auth.dto.users.UserBlockOutput
import com.api.factory.auth.dto.users.UserChangePasswordInput
import com.api.factory.auth.dto.users.UserChangePasswordOutput
import com.api.factory.auth.dto.users.UserCreateInput
import com.api.factory.auth.dto.users.UserOutput
import com.api.factory.auth.dto.users.UserUpdateInput
import com.api.factory.extensions.Result

interface IUserService {
    fun registerUser(body: UserCreateInput): Result<UserOutput>
    fun updateUser(body: UserUpdateInput): Result<UserOutput>
    fun blockUser(userId: Long): Result<UserBlockOutput>
    fun updatePassword(body: UserChangePasswordInput): Result<UserChangePasswordOutput>
    fun getUsers(): Result<List<UserOutput?>>
    fun getUser(id: Long): Result<UserOutput?>
}

