package com.api.factory.auth.models.users

import com.api.factory.auth.models.department.table.DepartmentTable
import com.api.factory.auth.models.users.table.UserTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object UsersDepartmentTable : Table(name = "users_department") {
    val user =
        reference(
            "user_id",
            UserTable,
            onDelete = ReferenceOption.CASCADE,
        )

    val department = reference("department_id", DepartmentTable)

    override val primaryKey = PrimaryKey(user, department, name = "USER_DEPARTMENT_PK")
}
