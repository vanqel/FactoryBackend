package com.api.factory.reporting.files.service

import com.api.factory.reporting.config.XLSXMultipartFile
import com.api.factory.reporting.core.enums.TypeFoundation
import com.api.factory.reporting.core.service.IReportService
import com.api.factory.statistic.service.IStatisticService
import com.api.factory.storage.core.service.FileOutput
import com.api.factory.storage.core.service.MinioService
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
    val storageService: MinioService,

    ) : IReportXlsxService {

    override fun generateReport(date: LocalDate): FileOutput {
        val data = reportService.getByDate(date)
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Report")

        val hRow = sheet.createRow(0)
        hRow.createCell(0).setCellValue("Отчёт за $date")


        val headerRow = sheet.createRow(1)
        val headers = listOf(
            "ID",
            "Объект",
            "Сортамент",
            "Отдел",
            "Начальник участка",
            "Дата",
            "Количество",
            "Установленная норма",
            "Общий вес",
            "Вес по установленной норме",
            "Дельта",
            "Процент выполнения",
            "Выполнено",
        )

        for ((index, header) in headers.withIndex()) {
            val cell = headerRow.createCell(index)
            cell.setCellValue(header)
            cell.cellStyle = createHeaderCellStyle(workbook)
        }

        for ((rowIndex, report) in data.withIndex()) {
            val row = sheet.createRow(rowIndex + 2)
            row.createCell(0).setCellValue(report.id.toString())
            row.createCell(1).setCellValue(report.obj.name)
            row.createCell(2).setCellValue(report.assortment.name)
            row.createCell(3).setCellValue(report.type.desription)
            row.createCell(4).setCellValue(report.user?.name ?: " -")
            row.createCell(5).setCellValue(report.date.toString())
            row.createCell(6).setCellValue(report.count.toString())
            row.createCell(7).setCellValue(report.normal.toString())
            row.createCell(8).setCellValue(report.getTotalWeight())
            row.createCell(9).setCellValue(report.getTotalWeightNormal())
            row.createCell(10).setCellValue(report.getDelta())
            row.createCell(11).setCellValue(report.getDeltaPercent())
            row.createCell(12).setCellValue(report.getPositive())

        }

        for (i in headers.indices) {
            sheet.autoSizeColumn(i)
        }

        val outputStream = ByteArrayOutputStream()
        workbook.write(outputStream)
        workbook.close()


        return storageService.addObject(
            XLSXMultipartFile(date.toString(), "oneDay", outputStream.toByteArray()),
            true
        )

    }

    override fun generateReportDayMonthYear(date: LocalDate): FileOutput {
        val data = statsService.getStatisticDayMonthTotal(date)

        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Report")

        val hRow = sheet.createRow(0)
        hRow.createCell(0).setCellValue("Общий отчёт")

        val headerRow = sheet.createRow(1)
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

        for ((index, header) in headers.withIndex()) {
            val cell = headerRow.createCell(index)
            cell.setCellValue(header)
            cell.cellStyle = createHeaderCellStyle(workbook)
        }

        data.entries.forEachIndexed { rowIndex, d ->
            val row = sheet.createRow(rowIndex + 2)
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

        return storageService.addObject(
            XLSXMultipartFile(date.toString(), "oneDay", outputStream.toByteArray()),
            true
        )

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


    override fun generateFullReport(dateStart: LocalDate, dateEnd: LocalDate): FileOutput {
        val data = statsService.getRatesByDatestamp(dateStart, dateEnd)

        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Report")

        val hRow = sheet.createRow(0)
        hRow.createCell(0).setCellValue("Отчёт за даты ${dateStart} - ${dateEnd}")

        val headerRow = sheet.createRow(1)
        val headers = listOf(
            "Объект / Выработка отдела",


            "${TypeFoundation.Assembly.desription} - факт",
            "${TypeFoundation.Welding.desription} - факт",
            "${TypeFoundation.Loading.desription} - факт",

            "${TypeFoundation.Assembly.desription} - норма",
            "${TypeFoundation.Welding.desription} - норма",
            "${TypeFoundation.Loading.desription} - норма",

            "${TypeFoundation.Assembly.desription} - дельта",
            "${TypeFoundation.Welding.desription} - дельта",
            "${TypeFoundation.Loading.desription} - дельта",

            "${TypeFoundation.Assembly.desription} - дельта %",
            "${TypeFoundation.Welding.desription} - дельта %",
            "${TypeFoundation.Loading.desription} - дельта %",

            TypeFoundation.Assembly.desription,
            TypeFoundation.Welding.desription,
            TypeFoundation.Loading.desription,

            )

        for ((index, header) in headers.withIndex()) {
            val cell = headerRow.createCell(index)
            cell.setCellValue(header)
            cell.cellStyle = createHeaderCellStyle(workbook)
        }

        data.entries.forEachIndexed { rowIndex, d ->
            val row = sheet.createRow(rowIndex + 2)
            row.createCell(0).setCellValue(d.key.name)

            row.createCell(1).setCellValue(d.value.find { it.type == TypeFoundation.Assembly }?.count ?: 0.0)
            row.createCell(2).setCellValue(d.value.find { it.type == TypeFoundation.Welding }?.count ?: 0.0)
            row.createCell(3).setCellValue(d.value.find { it.type == TypeFoundation.Loading }?.count ?: 0.0)

            row.createCell(4).setCellValue(d.value.find { it.type == TypeFoundation.Assembly }?.normal ?: 0.0)
            row.createCell(5).setCellValue(d.value.find { it.type == TypeFoundation.Welding }?.normal ?: 0.0)
            row.createCell(6).setCellValue(d.value.find { it.type == TypeFoundation.Loading }?.normal ?: 0.0)

            row.createCell(7).setCellValue(d.value.find { it.type == TypeFoundation.Assembly }?.getDelta() ?: 0.0)
            row.createCell(8).setCellValue(d.value.find { it.type == TypeFoundation.Welding }?.getDelta() ?: 0.0)
            row.createCell(9).setCellValue(d.value.find { it.type == TypeFoundation.Loading }?.getDelta() ?: 0.0)

            row.createCell(10)
                .setCellValue(d.value.find { it.type == TypeFoundation.Assembly }?.getDeltaPercent() ?: 0.0)
            row.createCell(11)
                .setCellValue(d.value.find { it.type == TypeFoundation.Welding }?.getDeltaPercent() ?: 0.0)
            row.createCell(12)
                .setCellValue(d.value.find { it.type == TypeFoundation.Loading }?.getDeltaPercent() ?: 0.0)

            row.createCell(13).setCellValue(d.value.find { it.type == TypeFoundation.Assembly }?.getPositive() ?: " ")
            row.createCell(14).setCellValue(d.value.find { it.type == TypeFoundation.Welding }?.getPositive() ?: " ")
            row.createCell(15).setCellValue(d.value.find { it.type == TypeFoundation.Loading }?.getPositive() ?: " ")


        }


        for (i in headers.indices) {
            sheet.autoSizeColumn(i)
        }

        val outputStream = ByteArrayOutputStream()
        workbook.write(outputStream)
        workbook.close()

        return storageService.addObject(
            XLSXMultipartFile("${dateStart} - ${dateEnd}", "oneDay", outputStream.toByteArray()),
            true
        )

    }


    override fun generateReportByDepartment(departId: Long, dateStart: LocalDate, dateEnd: LocalDate): FileOutput {
        val data = statsService.getRatesByObject(departId, dateStart, dateEnd)

        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Report")

        val hRow = sheet.createRow(0)
        hRow.createCell(0).setCellValue("Отчёт за даты ${dateStart} - ${dateEnd}")

        val headerRow = sheet.createRow(1)
        val headers = listOf(
            "Объект / Выработка отдела",

            "${TypeFoundation.Assembly.desription} - факт",
            "${TypeFoundation.Welding.desription} - факт",
            "${TypeFoundation.Loading.desription} - факт",

            "${TypeFoundation.Assembly.desription} - норма",
            "${TypeFoundation.Welding.desription} - норма",
            "${TypeFoundation.Loading.desription} - норма",

            "${TypeFoundation.Assembly.desription} - дельта",
            "${TypeFoundation.Welding.desription} - дельта",
            "${TypeFoundation.Loading.desription} - дельта",

            "${TypeFoundation.Assembly.desription} - дельта %",
            "${TypeFoundation.Welding.desription} - дельта %",
            "${TypeFoundation.Loading.desription} - дельта %",

            TypeFoundation.Assembly.desription,
            TypeFoundation.Welding.desription,
            TypeFoundation.Loading.desription,
        )

        for ((index, header) in headers.withIndex()) {
            val cell = headerRow.createCell(index)
            cell.setCellValue(header)
            cell.cellStyle = createHeaderCellStyle(workbook)
        }

        data.entries.forEachIndexed { rowIndex, d ->
            val row = sheet.createRow(rowIndex + 2)
            row.createCell(0).setCellValue(d.key.name)

            row.createCell(1).setCellValue(d.value.find { it.type == TypeFoundation.Assembly }?.count ?: 0.0)
            row.createCell(2).setCellValue(d.value.find { it.type == TypeFoundation.Welding }?.count ?: 0.0)
            row.createCell(3).setCellValue(d.value.find { it.type == TypeFoundation.Loading }?.count ?: 0.0)

            row.createCell(4).setCellValue(d.value.find { it.type == TypeFoundation.Assembly }?.normal ?: 0.0)
            row.createCell(5).setCellValue(d.value.find { it.type == TypeFoundation.Welding }?.normal ?: 0.0)
            row.createCell(6).setCellValue(d.value.find { it.type == TypeFoundation.Loading }?.normal ?: 0.0)

            row.createCell(7).setCellValue(d.value.find { it.type == TypeFoundation.Assembly }?.getDelta() ?: 0.0)
            row.createCell(8).setCellValue(d.value.find { it.type == TypeFoundation.Welding }?.getDelta() ?: 0.0)
            row.createCell(9).setCellValue(d.value.find { it.type == TypeFoundation.Loading }?.getDelta() ?: 0.0)

            row.createCell(10)
                .setCellValue(d.value.find { it.type == TypeFoundation.Assembly }?.getDeltaPercent() ?: 0.0)
            row.createCell(11)
                .setCellValue(d.value.find { it.type == TypeFoundation.Welding }?.getDeltaPercent() ?: 0.0)
            row.createCell(12)
                .setCellValue(d.value.find { it.type == TypeFoundation.Loading }?.getDeltaPercent() ?: 0.0)

            row.createCell(13).setCellValue(d.value.find { it.type == TypeFoundation.Assembly }?.getPositive() ?: " ")
            row.createCell(14).setCellValue(d.value.find { it.type == TypeFoundation.Welding }?.getPositive() ?: " ")
            row.createCell(15).setCellValue(d.value.find { it.type == TypeFoundation.Loading }?.getPositive() ?: " ")

        }


        for (i in headers.indices) {
            sheet.autoSizeColumn(i)
        }

        val outputStream = ByteArrayOutputStream()
        workbook.write(outputStream)
        workbook.close()

        return storageService.addObject(
            XLSXMultipartFile("${dateStart} - ${dateEnd}", "oneDay", outputStream.toByteArray()),
            true
        )
    }
}

