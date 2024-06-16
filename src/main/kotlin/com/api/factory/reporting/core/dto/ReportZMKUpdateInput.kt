package com.api.factory.reporting.core.dto

import com.api.factory.reporting.core.enums.TypeFoundation
import java.time.LocalDate

data class ReportZMKUpdateInput(
    val obj: Long,
    val assortment: Long,
    val type: TypeFoundation,
    val date: LocalDate,
    val count: Long,
    val image: String
)
