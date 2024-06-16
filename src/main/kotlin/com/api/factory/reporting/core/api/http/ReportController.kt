package com.api.factory.reporting.core.api.http

import com.api.factory.config.RemoveOutput
import com.api.factory.reporting.core.dto.ReportZMKCreateInput
import com.api.factory.reporting.core.dto.ReportZMKOutput
import com.api.factory.reporting.core.dto.ReportZMKUpdateInput
import com.api.factory.reporting.core.service.IReportService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reporting")
class ReportController(
    val reportService: IReportService,
) {
    @GetMapping()
    fun getAll(): List<ReportZMKOutput> =
        reportService.getAll()

    @PostMapping
    fun save(
        @RequestBody inputReport: ReportZMKCreateInput,
    ): ReportZMKOutput = reportService.save(inputReport)

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
