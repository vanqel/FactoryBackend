package com.api.factory.dictionary.assortment.api.http

import com.api.factory.config.RemoveOutput
import com.api.factory.dictionary.DictionaryRegApi
import com.api.factory.dictionary.assortment.dto.AssortmentInput
import com.api.factory.dictionary.assortment.dto.AssortmentOutput
import com.api.factory.dictionary.assortment.dto.AssortmentUpdateInput
import com.api.factory.dictionary.assortment.service.IAssortmentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(DictionaryRegApi.ASSORTMENT)
class AssortmentController(
    val service: IAssortmentService,
) {
    @PostMapping
    fun saveAssortment(@RequestBody body: AssortmentInput): AssortmentOutput =
        service.saveAssortment(body)

    @PutMapping
    fun updateAssortment(@RequestBody body: AssortmentUpdateInput): AssortmentOutput =
        service.updateAssortment(body)

    @DeleteMapping("{id}")
    fun deleteAssortment(@PathVariable id: Long): RemoveOutput =
        service.deleteAssortment(id)

    @GetMapping
    fun getAllAssortment(): List<AssortmentOutput> =
        service.getAllAssortment()

    @GetMapping("{id}")
    fun getAssortmentById(@PathVariable id: Long): AssortmentOutput =
        service.getAssortmentById(id)
}
