package com.api.factory.reporting.files.service

import com.api.factory.reporting.core.service.IReportService
import com.api.factory.statistic.service.IStatisticService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@Service
class ReportXlsxService(
    val statsService: IStatisticService,
    val reportService: IReportService
) : IReportXlsxService{
    override fun generateReport( date: LocalDate): MultipartFile {
        val data =  reportService.getByDate(date)
        TODO("Not yet implemented")

    }

    override fun generateReport(reportId: Long, date: LocalDate): MultipartFile {
        TODO("Not yet implemented")
    }

    override fun generateFullReport(dateStart: LocalDate, dateEnd: LocalDate): MultipartFile {
        TODO("Not yet implemented")
    }

    override fun generateReportByDepartment(departId: Long, dateStart: LocalDate, dateEnd: LocalDate): MultipartFile {
        TODO("Not yet implemented")
    }
}
