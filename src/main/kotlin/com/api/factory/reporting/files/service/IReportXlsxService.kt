package com.api.factory.reporting.files.service

import com.api.factory.reporting.config.XLSXMultipartFile
import com.api.factory.storage.core.service.FileOutput
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

interface IReportXlsxService {

    fun generateReport(date: LocalDate): FileOutput

    fun generateReportDayMonthYear(date: LocalDate): FileOutput

    fun generateFullReport(dateStart: LocalDate, dateEnd: LocalDate): FileOutput

    fun generateReportByDepartment(departId: Long, dateStart: LocalDate, dateEnd: LocalDate): FileOutput
}
