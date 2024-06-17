package com.api.factory.reporting.config

import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream

class XLSXMultipartFile(
    private val date: String,
    private val type: String,
    private val inputStream: ByteArray,
) : MultipartFile {

    override fun getInputStream(): InputStream {
        return ByteArrayInputStream(inputStream)
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
        return getInputStream().available() == 0
    }

    override fun getSize(): Long {
        return getInputStream().available().toLong()
    }

    override fun getBytes(): ByteArray {
        return inputStream
    }

    override fun transferTo(dest: File) {
        getInputStream().copyTo(dest.outputStream())
    }
}
