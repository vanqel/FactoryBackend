package com.api.factory.auth.models.department.table

import com.api.factory.config.ExtendedLongIdTable
import org.jetbrains.exposed.sql.Column

object DepartmentTable : ExtendedLongIdTable(name = "depratment") {
    var name: Column<String> = varchar("name", length = 300)
    var foundation: Column<Boolean> = bool("foundation").default(false)
}
