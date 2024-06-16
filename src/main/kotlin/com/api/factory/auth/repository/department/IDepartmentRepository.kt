package com.api.factory.auth.repository.department

import com.api.factory.auth.dto.department.DepartmentInput
import com.api.factory.auth.dto.department.DepartmentOutput
import com.api.factory.auth.models.department.DepartmentEntity

interface IDepartmentRepository {
    fun save(body: DepartmentInput): DepartmentEntity
    fun update(body: DepartmentOutput) : DepartmentEntity
    fun delete(id: Long) : Boolean
    fun getDepartment(id: Long) : DepartmentEntity
    fun getDepartmentByName(name: String) : DepartmentEntity
    fun getDepartments(): List<DepartmentEntity>
}
