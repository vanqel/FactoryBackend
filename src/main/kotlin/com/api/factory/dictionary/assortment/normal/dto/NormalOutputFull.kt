package com.api.factory.dictionary.assortment.normal.dto

import com.api.factory.dictionary.assortment.dto.AssortmentOutput
import java.time.LocalDate

data class NormalOutputFull(
    val objId: AssortmentOutput,
    val count: Long,
    val date: LocalDate,
)
