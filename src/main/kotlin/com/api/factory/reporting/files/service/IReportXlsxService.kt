package com.api.factory.reporting.files.service

import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

interface IReportXlsxService {

    fun generateReport( date: LocalDate): MultipartFile

    fun generateReport(reportId: Long, date: LocalDate): MultipartFile

    fun generateFullReport(dateStart: LocalDate, dateEnd: LocalDate): MultipartFile

    fun generateReportByDepartment(departId: Long, dateStart: LocalDate, dateEnd: LocalDate): MultipartFile
}
