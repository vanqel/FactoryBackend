package com.api.factory.dictionary.objects.models

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object ObjectsTable: LongIdTable("objects") {
    val name: Column<String> = varchar("name", 255).uniqueIndex()
    val archived: Column<Boolean> = bool("archived").default(false)
}
