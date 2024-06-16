package com.api.factory.dictionary.assortment.normal.dto

import com.api.factory.dictionary.assortment.dto.AssortmentOutput
import java.time.LocalDateTime

data class NormalOutputFull(
    val objId: AssortmentOutput,
    val count: Long,
    val date: LocalDateTime,
)
