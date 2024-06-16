package com.api.factory.auth.dto.users

import com.api.factory.auth.dto.token.TokenOutput
import com.api.factory.auth.models.users.UserEntity

data class UserTokenOutput(
    val userOutput: UserOutput,
    val tokenOutput: TokenOutput,
) {
    constructor(u: UserEntity, t: TokenOutput) : this(UserOutput(u), t)
}
