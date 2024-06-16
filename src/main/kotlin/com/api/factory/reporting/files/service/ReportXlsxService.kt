package com.api.factory.reporting.files.service

import com.api.factory.reporting.config.XLSXMultipartFile
import com.api.factory.reporting.core.service.IReportService
import com.api.factory.statistic.service.IStatisticService
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.*
import java.time.LocalDate

@Service
class ReportXlsxService(
    val statsService: IStatisticService,
    val reportService: IReportService,
) : IReportXlsxService {

    override fun generateReport(date: LocalDate): XLSXMultipartFile {
        val data = reportService.getByDate(date)
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Report")

        val headerRow = sheet.createRow(0)
        val headers = listOf(
            "ID",
            "Объект",
            "Сортамент",
            "Отдел",
            "Дата",
            "Количество",
            "Установленная норма",
            "Общий вес",
            "Вес по установленной норме"
        )

        // Create header cells
        for ((index, header) in headers.withIndex()) {
            val cell = headerRow.createCell(index)
            cell.setCellValue(header)
            cell.cellStyle = createHeaderCellStyle(workbook)
        }

        for ((rowIndex, report) in data.withIndex()) {
            val row = sheet.createRow(rowIndex + 1)
            row.createCell(0).setCellValue(report.id.toString())
            row.createCell(1).setCellValue(report.obj.name)
            row.createCell(2).setCellValue(report.assortment.name)
            row.createCell(3).setCellValue(report.type.desription)
            row.createCell(4).setCellValue(report.date.toString())
            row.createCell(5).setCellValue(report.count.toString())
            row.createCell(6).setCellValue(report.normal.toString())
            row.createCell(7).setCellValue(report.getTotalWeight())
            row.createCell(8).setCellValue(report.getTotalWeightNormal())
        }

        for (i in headers.indices) {
            sheet.autoSizeColumn(i)
        }

        val outputStream = ByteArrayOutputStream()
        workbook.write(outputStream)
        workbook.close()

        val inputStream: InputStream = ByteArrayInputStream(outputStream.toByteArray())

        return XLSXMultipartFile(date, "oneDay", inputStream)

    }
    private fun createHeaderCellStyle(workbook: XSSFWorkbook): XSSFCellStyle? {
        val style = workbook.createCellStyle()
        val font = workbook.createFont()

        font.bold = true
        font.color = IndexedColors.BLACK.index
        style.setFont(font)
        style.fillForegroundColor = IndexedColors.DARK_BLUE.index
        return style
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

