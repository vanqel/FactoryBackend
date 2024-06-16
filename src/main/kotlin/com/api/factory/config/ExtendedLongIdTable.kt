package com.api.factory.config

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

abstract class ExtendedLongIdTable(name: String) : LongIdTable(name) {
    init {
        Tables.add(this)
    }

    val createdAt = datetime("createdAt").default(LocalDateTime.now())
    val updatedAt = datetime("updatedAt").nullable()
    val deletedAt = datetime("deleted_at").nullable().index()
}
