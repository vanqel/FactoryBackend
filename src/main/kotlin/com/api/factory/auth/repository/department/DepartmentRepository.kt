package com.api.factory.auth.repository.department

import com.api.factory.auth.dto.department.DepartmentInput
import com.api.factory.auth.dto.department.DepartmentOutput
import com.api.factory.auth.errors.GeneralError
import com.api.factory.auth.models.department.DepartmentEntity
import com.api.factory.auth.models.department.table.DepartmentTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class DepartmentRepository : IDepartmentRepository {

    override fun save(body: DepartmentInput): DepartmentEntity {
        return DepartmentEntity.new {
            name = body.name
            foundation = body.foundation
        }
    }

    override fun update(body: DepartmentOutput): DepartmentEntity {
        return DepartmentEntity.findByIdAndUpdate(body.id) {
            it.name = body.name
            it.foundation = body.foundation
        } ?: throw GeneralError("Отдел не найден")
    }

    override fun delete(id: Long): Boolean {
        return if (DepartmentTable.deleteWhere { DepartmentTable.id eq id } == 1) true
        else throw GeneralError("Отдел не найден")
    }

    override fun getDepartment(id: Long): DepartmentEntity {
        return DepartmentEntity.findById(id) ?: throw GeneralError("Отдел не найден")
    }

    override fun getDepartmentByName(name: String): DepartmentEntity {
        return DepartmentEntity.find { DepartmentTable.name eq name }.firstOrNull()
            ?: throw GeneralError("Отдел не найден")
    }

    override fun getDepartments(): List<DepartmentEntity> {
        return DepartmentEntity.all().toList().ifEmpty { throw GeneralError("Отделы не найдены") }
    }
}
