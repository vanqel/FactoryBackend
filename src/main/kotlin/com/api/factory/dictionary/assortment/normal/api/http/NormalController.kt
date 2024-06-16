package com.api.factory.dictionary.assortment.normal.api.http

import com.api.factory.dictionary.DictionaryRegApi
import com.api.factory.dictionary.assortment.normal.dto.NormalInput
import com.api.factory.dictionary.assortment.normal.dto.NormalOutputFull
import com.api.factory.dictionary.assortment.normal.service.NormalService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Рабочий план")
@RequestMapping(DictionaryRegApi.NORMAL)
class NormalController(
    val service: NormalService
) {
    @GetMapping
    fun getNormal(): List<NormalOutputFull> {
        return service.getAllNormal()
    }

    @PostMapping
    fun createNormal(@RequestBody normal: NormalInput): NormalOutputFull {
        return service.putNormal(normal)
    }
}
