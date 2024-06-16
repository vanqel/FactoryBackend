package com.api.factory.statistic.dto

import java.time.LocalDate

data class NormalInput(
    val objId: Long,
    val count: Long,
    val date: LocalDate,
)
