package com.api.factory.dictionary.objects.api.http

import com.api.factory.config.RemoveOutput
import com.api.factory.dictionary.DictionaryRegApi
import com.api.factory.dictionary.objects.dto.ObjectInput
import com.api.factory.dictionary.objects.dto.ObjectOutput
import com.api.factory.dictionary.objects.dto.ObjectUpdateInput
import com.api.factory.dictionary.objects.service.IObjectService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(DictionaryRegApi.OBJECTS)
class ObjectsController(
    val service: IObjectService,
) {
    @GetMapping
    fun getAllObjects(): List<ObjectOutput> =
        service.getAllObjects()

    @GetMapping("{id}")
    fun getObjectById(@PathVariable id: Long): ObjectOutput =
        service.getObjectById(id)

    @PostMapping
    fun saveObject(@RequestBody body: ObjectInput): ObjectOutput =
        service.saveObject(body)

    @PutMapping
    fun updateObject(@RequestBody body: ObjectUpdateInput): ObjectOutput =
        service.updateObject(body)


    @DeleteMapping("{id}")
    fun deleteObject(@PathVariable id: Long): RemoveOutput =
        service.deleteObject(id)

}
