package com.api.factory.reporting.files.api.http

import com.api.factory.reporting.files.service.IReportXlsxService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    ): ResponseEntity<ByteArray> {
        val result = service.generateReport(date)
        val headers = HttpHeaders()

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${result.originalFilename}")
        headers.add(HttpHeaders.CONTENT_TYPE, result.contentType)

        return ResponseEntity(result.bytes, headers, HttpStatus.OK)
    }
}
