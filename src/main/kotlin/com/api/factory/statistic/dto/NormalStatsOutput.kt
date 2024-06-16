package com.api.factory.statistic.dto

data class NormalStatsOutput(
    val data: List<StatsByTypeSum>,
    val normal: Double
)
