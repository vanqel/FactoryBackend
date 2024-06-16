package com.api.factory.auth.api.http

import com.api.factory.auth.dto.department.DepartmentDeleteOutput
import com.api.factory.auth.dto.department.DepartmentInput
import com.api.factory.auth.dto.department.DepartmentOutput
import com.api.factory.auth.services.department.IDepartmentService
import com.github.michaelbull.result.getOrThrow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/department")
class DepartmentController(
    val service: IDepartmentService,
) {
    @PostMapping
    fun save(
        @RequestBody body: DepartmentInput,
    ): ResponseEntity<DepartmentOutput> =
        ResponseEntity.ok(service.save(body).getOrThrow())

    @PutMapping
    fun update(
        @RequestBody body: DepartmentOutput,
    ): ResponseEntity<DepartmentOutput> =
        ResponseEntity.ok(service.update(body).getOrThrow())

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long,
    ): ResponseEntity<DepartmentDeleteOutput> =
        ResponseEntity.ok(service.delete(id).getOrThrow())

    @GetMapping("/{id}")
    fun getDepartment(
        @PathVariable id: Long,
    ): ResponseEntity<DepartmentOutput> =
        ResponseEntity.ok(service.getDepartment(id).getOrThrow())

    @GetMapping
    fun getDepartments(): ResponseEntity<List<DepartmentOutput>> =
        ResponseEntity.ok(service.getDepartments().getOrThrow())
}
