package com.api.factory.reporting.core.models

import com.api.factory.auth.models.department.table.DepartmentTable
import com.api.factory.auth.models.users.table.UserTable
import com.api.factory.dictionary.assortment.models.AssortmentTable
import com.api.factory.dictionary.objects.models.ObjectsTable
import com.api.factory.reporting.core.enums.TypeFoundation
import com.api.factory.statistic.models.NormalTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object ReportZMKTable : LongIdTable("zmk_reporting") {
    val user = reference("user_id", UserTable, onDelete = ReferenceOption.CASCADE)
    val department = reference("department_id", DepartmentTable, onDelete = ReferenceOption.CASCADE)
    val obj = reference("object_id", ObjectsTable, onDelete = ReferenceOption.CASCADE)
    val assortment = reference("sortment_id", AssortmentTable, onDelete = ReferenceOption.CASCADE)
    val date: Column<LocalDate> = date("date")
    val count: Column<Long> = long("count")
    val img = uuid("image_key")
    val type = enumeration("type", TypeFoundation::class)
    val normal = reference("normal_id", NormalTable, onDelete = ReferenceOption.CASCADE).nullable()
}
