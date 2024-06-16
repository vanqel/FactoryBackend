package com.api.factory.statistic.dto

import com.api.factory.reporting.core.enums.TypeFoundation

data class StatsTypeOutput(
    val type: TypeFoundation,
    val actual: Double,
    val prev: Double,
    var count: Double,
    val isGood: Boolean,
    val normalToday: Double,
    val normalPrev: Double
)

