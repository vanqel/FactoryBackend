package com.api.factory.auth.services.department

import com.api.factory.auth.dto.department.DepartmentDeleteOutput
import com.api.factory.auth.dto.department.DepartmentInput
import com.api.factory.auth.dto.department.DepartmentOutput
import com.api.factory.extensions.Result

interface IDepartmentService {
    fun save(body: DepartmentInput): Result<DepartmentOutput>
    fun update(body: DepartmentOutput): Result<DepartmentOutput>
    fun delete(id: Long): Result<DepartmentDeleteOutput>
    fun getDepartment(id: Long): Result<DepartmentOutput>
    fun getDepartmentByName(name: String): Result<DepartmentOutput>
    fun getDepartments(): Result<List<DepartmentOutput>>
}

