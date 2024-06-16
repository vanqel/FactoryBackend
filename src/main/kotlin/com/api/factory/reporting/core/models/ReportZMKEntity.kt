package com.api.factory.reporting.core.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ReportZMKEntity(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<ReportZMKEntity>(ReportZMKTable)

    var user by  ReportZMKTable.user
    var department by ReportZMKTable.department
    var obj by ReportZMKTable.obj
    var assortment by ReportZMKTable.assortment
    var date by ReportZMKTable.date
    var count by ReportZMKTable.count
    var img by ReportZMKTable.img
    var type by ReportZMKTable.type


}
