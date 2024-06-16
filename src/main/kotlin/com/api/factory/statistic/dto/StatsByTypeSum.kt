package com.api.factory.statistic.dto

import com.api.factory.reporting.core.enums.TypeFoundation

data class StatsByTypeSum(
    val type: TypeFoundation,
    val count: Double,
    val normal: Double
)
