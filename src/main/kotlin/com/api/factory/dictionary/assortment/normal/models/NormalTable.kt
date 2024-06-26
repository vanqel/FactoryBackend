package com.api.factory.dictionary.assortment.normal.models

import com.api.factory.dictionary.assortment.models.AssortmentTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime

object NormalTable: LongIdTable("normal") {
    val obj = reference("obj", AssortmentTable, onDelete = ReferenceOption.CASCADE)
    val count = long("count")
    val date = datetime("date")
}
