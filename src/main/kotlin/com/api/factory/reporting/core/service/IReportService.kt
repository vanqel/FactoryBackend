package com.api.factory.reporting.core.service

import com.api.factory.config.RemoveOutput
import com.api.factory.reporting.core.dto.ReportZMKCreateInput
import com.api.factory.reporting.core.dto.ReportZMKOutput
import com.api.factory.reporting.core.dto.ReportZMKUpdateInput
import com.api.factory.reporting.core.models.ReportZMKEntity

interface IReportService {
    fun getDTOByOutput(report: ReportZMKEntity): ReportZMKOutput

    fun saveBatch(inp: List<ReportZMKCreateInput>): Boolean
    fun save(inputReport: ReportZMKCreateInput): ReportZMKOutput
    fun update(id: Long, inputReport: ReportZMKUpdateInput): ReportZMKOutput
    fun getById(id: Long): ReportZMKOutput
    fun delete(id: Long): RemoveOutput
    fun getAll(): List<ReportZMKOutput>
}
