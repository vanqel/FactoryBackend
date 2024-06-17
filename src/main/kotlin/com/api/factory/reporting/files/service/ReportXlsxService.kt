package com.api.factory.reporting.files.service

import com.api.factory.reporting.config.XLSXMultipartFile
import com.api.factory.reporting.core.enums.TypeFoundation
import com.api.factory.reporting.core.service.IReportService
import com.api.factory.statistic.service.IStatisticService
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.time.LocalDate

@Service
class ReportXlsxService(
    val statsService: IStatisticService,
    val reportService: IReportService,
    val objectMapper: ObjectMapper
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

    override fun generateReportDayMonthYear(date: LocalDate): XLSXMultipartFile {
        val data = statsService.getStatisticDayMonthTotal(date)

        println(objectMapper.writeValueAsString(data))
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Report")

        val headerRow = sheet.createRow(0)
        val headers = listOf(
            "Объект / Выработка отдела",

            "${TypeFoundation.Assembly.desription} за сутки",
            "${TypeFoundation.Welding.desription} за сутки",
            "${TypeFoundation.Loading.desription} за сутки",

            " ${TypeFoundation.Assembly.desription} за месяц",
            " ${TypeFoundation.Welding.desription} за месяц",
            " ${TypeFoundation.Loading.desription} за месяц",

            " ${TypeFoundation.Assembly.desription} за всё время",
            " ${TypeFoundation.Welding.desription} за всё время",
            " ${TypeFoundation.Loading.desription} за всё время",
        )

        // Create header cells
        for ((index, header) in headers.withIndex()) {
            val cell = headerRow.createCell(index)
            cell.setCellValue(header)
            cell.cellStyle = createHeaderCellStyle(workbook)
        }

        data.entries.forEachIndexed { rowIndex, d ->
            val row = sheet.createRow(rowIndex + 1)
            row.createCell(0).setCellValue(d.key.name)

            row.createCell(1).setCellValue(d.value.day.find { it.type == TypeFoundation.Assembly }?.count ?: 0.0)
            row.createCell(2).setCellValue(d.value.day.find { it.type == TypeFoundation.Welding }?.count ?: 0.0)
            row.createCell(3).setCellValue(d.value.day.find { it.type == TypeFoundation.Loading }?.count ?: 0.0)

            row.createCell(4).setCellValue(d.value.month.find { it.type == TypeFoundation.Assembly }?.count ?: 0.0)
            row.createCell(5).setCellValue(d.value.month.find { it.type == TypeFoundation.Welding }?.count ?: 0.0)
            row.createCell(6).setCellValue(d.value.month.find { it.type == TypeFoundation.Loading }?.count ?: 0.0)

            row.createCell(7).setCellValue(d.value.year.find { it.type == TypeFoundation.Assembly }?.count ?: 0.0)
            row.createCell(8).setCellValue(d.value.year.find { it.type == TypeFoundation.Welding }?.count ?: 0.0)
            row.createCell(9).setCellValue(d.value.year.find { it.type == TypeFoundation.Loading }?.count ?: 0.0)

        }


        for (i in headers.indices) {
            sheet.autoSizeColumn(i)
        }

        val outputStream = ByteArrayOutputStream()
        workbook.write(outputStream)
        workbook.close()

        val inputStream: InputStream = ByteArrayInputStream(outputStream.toByteArray())

        return XLSXMultipartFile(date, "full", inputStream)

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


    override fun generateFullReport(dateStart: LocalDate, dateEnd: LocalDate): MultipartFile {
        TODO("Not yet implemented")
    }

    override fun generateReportByDepartment(departId: Long, dateStart: LocalDate, dateEnd: LocalDate): MultipartFile {
        TODO("Not yet implemented")
    }
}

