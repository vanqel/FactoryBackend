package com.api.factory.statistic.dto

import com.api.factory.dictionary.objects.dto.ObjectOutput
import java.time.LocalDate

data class NormalOutputFull(
    val objId: ObjectOutput,
    val count: Long,
    val date: LocalDate,
)
