package com.api.factory.statistic.dto

import com.api.factory.reporting.core.enums.TypeFoundation

data class StatsObjectOutput(
    val type: TypeFoundation,
    val objId: Long,
    val obj: String,
    var count: Double,
)

data class StatsTypeOutput(
    val type: TypeFoundation,
    val actual: Double,
    val prev: Double,
    var count: Double,
    val isGood: Boolean
)

data class StatsByTypeSum(
    val type: TypeFoundation,
    val count: Double
)

data class StatsObjectDayMonthYear(
    val obj: String,
    val day: List<StatsByTypeSum>,
    val month:  List<StatsByTypeSum>,
    val year:  List<StatsByTypeSum>,
)
