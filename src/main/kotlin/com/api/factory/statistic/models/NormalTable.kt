package com.api.factory.statistic.models

import com.api.factory.dictionary.assortment.models.AssortmentTable
import com.api.factory.dictionary.objects.models.ObjectsTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.date

object NormalTable: LongIdTable("normal") {
    val obj = reference("obj", AssortmentTable, onDelete = ReferenceOption.CASCADE)
    val count = long("count")
    val date = date("date")
}
