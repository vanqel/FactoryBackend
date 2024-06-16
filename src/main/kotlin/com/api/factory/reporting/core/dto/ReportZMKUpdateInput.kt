package com.api.factory.reporting.core.dto

import com.api.factory.reporting.core.enums.TypeFoundation
import java.time.LocalDate

data class ReportZMKUpdateInput(
    val obj: Long,
    val assortment: Long,
    val type: TypeFoundation,
    val count: Long,
)
