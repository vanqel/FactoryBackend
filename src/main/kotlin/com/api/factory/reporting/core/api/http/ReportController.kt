package com.api.factory.reporting.core.api.http

import com.api.factory.config.RemoveOutput
import com.api.factory.reporting.core.dto.ReportZMKCreateInput
import com.api.factory.reporting.core.dto.ReportZMKOutput
import com.api.factory.reporting.core.dto.ReportZMKUpdateInput
import com.api.factory.reporting.core.service.IReportService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/reporting")
@Tag(name = "Отчеты")
@SecurityRequirement(name = "JWT")
class ReportController(
    val reportService: IReportService,
) {
    @GetMapping
    fun getByDate(
        @RequestParam date: LocalDate,
    ) = reportService.getByDate(date)

    @GetMapping()
    fun getAll(): List<ReportZMKOutput> =
        reportService.getAll()

    @PostMapping
    fun save(
        @RequestBody inputReport: ReportZMKCreateInput,
    ): ReportZMKOutput = reportService.save(inputReport)

    @PostMapping("/batch")
    fun saveBatch(
        @RequestBody inputReport: List<ReportZMKCreateInput>,
    ): Boolean = reportService.saveBatch(inputReport)

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long,
    ): ReportZMKOutput = reportService.getById(id)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody inputReport: ReportZMKUpdateInput,
    ): ReportZMKOutput = reportService.update(id, inputReport)

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable id: Long): RemoveOutput = reportService.delete(id)
}
