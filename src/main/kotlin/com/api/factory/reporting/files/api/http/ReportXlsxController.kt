package com.api.factory.reporting.files.api.http

import com.api.factory.reporting.files.service.IReportXlsxService
import com.api.factory.storage.core.service.FileOutput
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@Tag(name = "Генерация xlsx отчетов")
@RequestMapping("api/report/files")
class ReportXlsxController(
    val service: IReportXlsxService,
) {

    @GetMapping
    fun generateReport(
        response: HttpServletResponse,
        @RequestParam date: LocalDate,
    ): FileOutput {
        return service.generateReport(date)

    }

    @GetMapping("/full")
    fun generateReportDayMonthYear(
        response: HttpServletResponse,
        @RequestParam date: LocalDate,
    ): FileOutput {
        return service.generateReportDayMonthYear(date)

    }

    @GetMapping("/stamp")
    fun generateReportStamp(
        response: HttpServletResponse,
        @RequestParam dateStart: LocalDate,
        @RequestParam dateEnd: LocalDate,
    ): FileOutput {
        return service.generateFullReport(dateStart, dateEnd)
    }

    @GetMapping("/department")
    fun generateReportStamp(
        response: HttpServletResponse,
        @RequestParam departId: Long,
        @RequestParam dateStart: LocalDate,
        @RequestParam dateEnd: LocalDate,
    ): FileOutput {
        return service.generateReportByDepartment(departId, dateStart, dateEnd)
    }
}
