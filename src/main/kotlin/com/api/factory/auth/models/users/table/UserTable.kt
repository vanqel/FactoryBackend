package com.api.factory.auth.models.users.table

import com.api.factory.config.ExtendedLongIdTable

object UserTable : ExtendedLongIdTable(name = "users") {
    val username = varchar("username", length = 100)

    val password = varchar("password", length = 100)

    val name = varchar("name", length = 200)

    var isBlocked = bool("isBlocked").default(false)

    init {
        uniqueIndex(
            customIndexName = "USERS_USERNAME_UNIQUE",
            columns = arrayOf(username),
        )
    }
}
