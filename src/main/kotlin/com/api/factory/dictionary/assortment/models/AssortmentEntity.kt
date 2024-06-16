package com.api.factory.dictionary.assortment.models

import com.api.factory.dictionary.assortment.dto.AssortmentOutput
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AssortmentEntity(id: EntityID<Long>): LongEntity(id) {

    companion object : LongEntityClass<AssortmentEntity>(AssortmentTable)

    var name by AssortmentTable.name
    var count by AssortmentTable.count
    var archived by AssortmentTable.archived

    fun toDTO()= AssortmentOutput(
        id = id.value,
        name = name,
        count = count
    )
}
