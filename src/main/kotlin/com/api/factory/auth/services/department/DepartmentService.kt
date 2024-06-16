package com.api.factory.auth.services.department

import com.api.factory.auth.dto.department.DepartmentDeleteOutput
import com.api.factory.auth.dto.department.DepartmentInput
import com.api.factory.auth.dto.department.DepartmentOutput
import com.api.factory.auth.errors.GeneralError
import com.api.factory.auth.models.department.DepartmentEntity
import com.api.factory.auth.repository.department.IDepartmentRepository
import com.api.factory.extensions.Result
import com.api.factory.extensions.error
import com.api.factory.extensions.ok
import org.springframework.stereotype.Service

@Service
class DepartmentService(
    val repository: IDepartmentRepository
) : IDepartmentService {
    override fun save(body: DepartmentInput): Result<DepartmentOutput> {
        return try {
            Result.ok(repository.save(body).toDTO())
        } catch (e: Exception) {
            return Result.error(GeneralError("Ошибка сохранения"))
        }

    }

    override fun update(body: DepartmentOutput): Result<DepartmentOutput> {
        return try {
            Result.ok(repository.update(body).toDTO())
        } catch (e: Exception) {
            return Result.error(GeneralError("Ошибка обновления"))
        }
    }

    override fun delete(id: Long): Result<DepartmentDeleteOutput> {
        return try {
            Result.ok(DepartmentDeleteOutput(repository.delete(id)))
        } catch (e: Exception) {
            return Result.error(GeneralError("Ошибка удаления"))
        }
    }

    override fun getDepartment(id: Long): Result<DepartmentOutput> {
        return try {
            Result.ok(repository.getDepartment(id).toDTO())
        } catch (e: Exception) {
            return Result.error(GeneralError("Ошибка удаления"))
        }
    }

    override fun getDepartmentByName(name: String): Result<DepartmentOutput> {
        return try {
            Result.ok(repository.getDepartmentByName(name).toDTO())
        } catch (e: Exception) {
            return Result.error(GeneralError("Ошибка удаления"))
        }
    }

    override fun getDepartments(): Result<List<DepartmentOutput>> {
        return try {
            Result.ok(repository.getDepartments().map(DepartmentEntity::toDTO))
        } catch (e: Exception) {
            return Result.error(GeneralError("Ошибка удаления"))
        }
    }


}
