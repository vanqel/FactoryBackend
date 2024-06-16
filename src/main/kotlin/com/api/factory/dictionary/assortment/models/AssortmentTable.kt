package com.api.factory.dictionary.assortment.models

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object AssortmentTable: LongIdTable("sortament") {
    val name: Column<String> = varchar("name", 255).uniqueIndex()
    val count: Column<Double> = double("count")
    val archived: Column<Boolean> = bool("archived").default(false)
}
