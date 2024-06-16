package com.api.factory.auth.models.department

import com.api.factory.auth.dto.department.DepartmentOutput
import com.api.factory.auth.models.department.table.DepartmentTable
import com.api.factory.config.ExtendedLongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class DepartmentEntity(id: EntityID<Long>) : ExtendedLongEntity(id, DepartmentTable) {
    companion object : LongEntityClass<DepartmentEntity>(DepartmentTable)

    var name by DepartmentTable.name
    var foundation by DepartmentTable.foundation
    fun toDTO(): DepartmentOutput {
        return DepartmentOutput(
            id.value, name, foundation
        )
    }
}
