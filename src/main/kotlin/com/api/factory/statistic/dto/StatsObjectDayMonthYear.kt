package com.api.factory.statistic.dto

data class StatsObjectDayMonthYear(
    val obj: String,
    val day: List<StatsByTypeSum>,
    val month:  List<StatsByTypeSum>,
    val year:  List<StatsByTypeSum>,
)
