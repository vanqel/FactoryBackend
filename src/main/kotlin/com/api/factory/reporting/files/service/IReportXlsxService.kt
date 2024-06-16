package com.api.factory.reporting.files.service

import com.api.factory.reporting.config.XLSXMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

interface IReportXlsxService {

    fun generateReport(date: LocalDate): XLSXMultipartFile

    fun generateReportDayMonthYear(date: LocalDate): XLSXMultipartFile

    fun generateFullReport(dateStart: LocalDate, dateEnd: LocalDate): MultipartFile

    fun generateReportByDepartment(departId: Long, dateStart: LocalDate, dateEnd: LocalDate): MultipartFile
}
