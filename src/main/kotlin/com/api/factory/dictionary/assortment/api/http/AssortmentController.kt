package com.api.factory.dictionary.assortment.api.http

import com.api.factory.config.RemoveOutput
import com.api.factory.dictionary.DictionaryRegApi
import com.api.factory.dictionary.assortment.dto.AssortmentInput
import com.api.factory.dictionary.assortment.dto.AssortmentOutput
import com.api.factory.dictionary.assortment.dto.AssortmentUpdateInput
import com.api.factory.dictionary.assortment.service.IAssortmentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Ассортимент")
@RequestMapping(DictionaryRegApi.ASSORTMENT)
class AssortmentController(
    val service: IAssortmentService,
) {
    @Operation(
        summary = "Создание нового ассортимента",
        description = """
        N_EXPORT = "Прораб по отгрузке"
        ADMIN = "ADMIN"
        DIMK = "Руководитель ДИМК"
        """,
        security = [SecurityRequirement(name = "JWT")]
    )
    @PostMapping
    fun saveAssortment(@RequestBody body: AssortmentInput): AssortmentOutput =
        service.saveAssortment(body)


    @Operation(
        summary = "Обновление ассортимента",
        description = """
        N_EXPORT = "Прораб по отгрузке"
        ADMIN = "ADMIN"
        DIMK = "Руководитель ДИМК"
        """,
        security = [SecurityRequirement(name = "JWT")]

    )
    @PutMapping
    fun updateAssortment(@RequestBody body: AssortmentUpdateInput): AssortmentOutput =
        service.updateAssortment(body)

    @Operation(
        summary = "Архивация ассортимента",
        description = """
        N_EXPORT = "Прораб по отгрузке"
        ADMIN = "ADMIN"
        DIMK = "Руководитель ДИМК"
        """,
        security = [SecurityRequirement(name = "JWT")]

    )
    @DeleteMapping("{id}")
    fun deleteAssortment(@PathVariable id: Long): RemoveOutput =
        service.deleteAssortment(id)

    @Operation(
        summary = "Получение всех ассортиментов",
    )
    @GetMapping
    fun getAllAssortment(): List<AssortmentOutput> =
        service.getAllAssortment()

    @Operation(
        summary = "Получение конкретного ассортиментов",
    )
    @GetMapping("{id}")
    fun getAssortmentById(@PathVariable id: Long): AssortmentOutput =
        service.getAssortmentById(id)
}
