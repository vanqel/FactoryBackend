package com.api.factory.statistic.models

import com.api.factory.dictionary.objects.models.ObjectsTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.date

object NormalTable: LongIdTable("normal") {
    val obj = reference("obj", ObjectsTable, onDelete = ReferenceOption.CASCADE)
    val count = integer("count")
    val date = date("date")
}
