package com.api.factory.storage.images.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class ImageEntity (id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<ImageEntity>(ImagesTable)

    val image by ImagesTable.id
    var parentKey by ImagesTable.parentKey
}
