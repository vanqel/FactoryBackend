package com.api.factory.statistic.models

import com.api.factory.dictionary.objects.models.ObjectEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.id.EntityID

class NormalEntity(id: EntityID<Long>): LongEntity(id) {
    var obj by ObjectEntity referencedOn NormalTable.obj
    var count by NormalTable.count
    var date by NormalTable.date
}
