package com.api.factory.reporting.files.api.http

import com.api.factory.reporting.files.service.IReportXlsxService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import jdk.jfr.ContentType
import org.apache.poi.openxml4j.opc.ContentTypes
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
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
    ): ResponseEntity<InputStream> {
        val result = service.generateReport(date)
        val headers = HttpHeaders()

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${result.originalFilename}")
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream")

        return ResponseEntity(result.inputStream, headers, HttpStatus.OK)
    }

    @GetMapping("/full")
    fun generateReportDayMonthYear(
        response: HttpServletResponse,
        @RequestParam date: LocalDate,
    ): ResponseEntity<ByteArray> {
        val result = service.generateReportDayMonthYear(date)
        val headers = HttpHeaders()

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${result.originalFilename}")
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream")

        return ResponseEntity(result.bytes, headers, HttpStatus.OK)
    }
}
