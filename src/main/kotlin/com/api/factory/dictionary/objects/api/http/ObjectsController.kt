package com.api.factory.dictionary.objects.api.http

import com.api.factory.config.RemoveOutput
import com.api.factory.dictionary.DictionaryRegApi
import com.api.factory.dictionary.objects.dto.ObjectInput
import com.api.factory.dictionary.objects.dto.ObjectOutput
import com.api.factory.dictionary.objects.dto.ObjectUpdateInput
import com.api.factory.dictionary.objects.service.IObjectService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Объекты")
@RequestMapping(DictionaryRegApi.OBJECTS)
class ObjectsController(
    val service: IObjectService,
) {
    @Operation(
        summary = "Получение всех объектов",
    )
    @GetMapping
    fun getAllObjects(): List<ObjectOutput> =
        service.getAllObjects()

    @Operation(
        summary = "Получение конкретного объекта",
    )
    @GetMapping("{id}")
    fun getObjectById(@PathVariable id: Long): ObjectOutput =
        service.getObjectById(id)

    @Operation(
        summary = "Создание объекта",
        description = """
        N_EXPORT = "Прораб по отгрузке"
        ADMIN = "ADMIN"
        DIMK = "Руководитель ДИМК"
        """,
        security = [SecurityRequirement(name = "JWT")]

    )
    @PostMapping
    fun saveObject(@RequestBody body: ObjectInput): ObjectOutput =
        service.saveObject(body)
    @Operation(
        summary = "Обновление объекта",
        description = """
        N_EXPORT = "Прораб по отгрузке"
        ADMIN = "ADMIN"
        DIMK = "Руководитель ДИМК"
        """,
        security = [SecurityRequirement(name = "JWT")]

    )
    @PutMapping
    fun updateObject(@RequestBody body: ObjectUpdateInput): ObjectOutput =
        service.updateObject(body)

    @Operation(
        summary = "Архивация объекта",
        description = """
        N_EXPORT = "Прораб по отгрузке"
        ADMIN = "ADMIN"
        DIMK = "Руководитель ДИМК"
        """,
        security = [SecurityRequirement(name = "JWT")]

    )
    @DeleteMapping("{id}")
    fun deleteObject(@PathVariable id: Long): RemoveOutput =
        service.deleteObject(id)

}
