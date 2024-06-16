package com.api.factory.auth.models.authorites.table

import com.api.factory.config.ExtendedLongIdTable
import com.api.factory.auth.models.users.table.UserTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption

object UserLoginTable : ExtendedLongIdTable(name = "user_auth") {
    val user =
        reference(
            "user_id",
            UserTable,
            onDelete = ReferenceOption.CASCADE,
        )

    val accesstoken: Column<String> = varchar(name = "accessToken", length = 1000)

    val refreshtoken: Column<String> = varchar(name = "refreshToken", length = 500)
}
