package com.api.factory.dictionary.assortment.normal.models

import com.api.factory.dictionary.assortment.models.AssortmentEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class NormalEntity(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<NormalEntity>(NormalTable)

    var obj by AssortmentEntity referencedOn NormalTable.obj
    var count by NormalTable.count
    var date by NormalTable.date
}
