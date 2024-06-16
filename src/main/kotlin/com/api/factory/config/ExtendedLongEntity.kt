package com.api.factory.config

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime

abstract class ExtendedLongEntity(
    id: EntityID<Long>,
    table: ExtendedLongIdTable,
) : LongEntity(id) {
    val createdAt by table.createdAt
    var updatedAt by table.updatedAt
    var deletedAt by table.deletedAt

    fun hardDelete() = super.delete()

    override fun delete() {
        deletedAt = LocalDateTime.now()
    }
}
