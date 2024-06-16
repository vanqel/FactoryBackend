package com.api.factory.dictionary.objects.models

import com.api.factory.dictionary.objects.dto.ObjectOutput
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ObjectEntity(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<ObjectEntity>(ObjectsTable)

    var name by ObjectsTable.name
    var archived by ObjectsTable.archived

    fun toDTO() = ObjectOutput(
        id = id.value,
        name = name,
    )


}
