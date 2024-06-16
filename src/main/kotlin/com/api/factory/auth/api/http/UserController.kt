package com.api.factory.auth.api.http

import com.api.factory.auth.dto.users.UserBlockOutput
import com.api.factory.auth.dto.users.UserChangePasswordInput
import com.api.factory.auth.dto.users.UserChangePasswordOutput
import com.api.factory.auth.dto.users.UserCreateInput
import com.api.factory.auth.dto.users.UserOutput
import com.api.factory.auth.dto.users.UserUpdateInput
import com.api.factory.auth.services.user.IUserService
import com.github.michaelbull.result.getOrThrow
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: IUserService,
) {
    @PostMapping("register")
    fun register(
        @RequestBody body: UserCreateInput,
    ): ResponseEntity<UserOutput> {
        return ok(userService.registerUser(body).getOrThrow())
    }

    @PostMapping("updateUser")
    fun updateUser(
        @RequestBody body: UserUpdateInput,
    ): ResponseEntity<UserOutput> {
        return ok(userService.updateUser(body).getOrThrow())
    }

    @PostMapping("blockUser")
    fun blockUser(
        @RequestParam userId: Long,
    ): ResponseEntity<UserBlockOutput> {
        return ok(userService.blockUser( userId).getOrThrow())
    }

    @PostMapping("updatePassword")
    fun updatePassword(
        @RequestBody body: UserChangePasswordInput,
    ): ResponseEntity<UserChangePasswordOutput> {
        return ok(userService.updatePassword(body).getOrThrow())
    }

    @GetMapping
    fun getUsers(): ResponseEntity<List<UserOutput?>> {
        return ok(userService.getUsers().getOrThrow())
    }

    @GetMapping("{userId}")
    fun getUser(@PathVariable userId: Long): ResponseEntity<UserOutput?> {
        return ok(userService.getUser(userId).getOrThrow())
    }
}
