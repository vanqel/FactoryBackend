package com.api.factory.reporting.config

import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.InputStream
import java.time.LocalDate

class XLSXMultipartFile(
    private val date: LocalDate,
    private val type: String,
    private val inputStream: InputStream,
) : MultipartFile {
    override fun getInputStream(): InputStream {
        return inputStream
    }

    override fun getName(): String {
        return "report-${date}-${type}.xlsx"
    }

    override fun getOriginalFilename(): String? {
        return name
    }

    override fun getContentType(): String? {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    }

    override fun isEmpty(): Boolean {
        return inputStream.available() == 0
    }

    override fun getSize(): Long {
        return inputStream.available().toLong()
    }

    override fun getBytes(): ByteArray {
        return inputStream.readBytes()
    }

    override fun transferTo(dest: File) {
        inputStream.copyTo(dest.outputStream())
    }
}
