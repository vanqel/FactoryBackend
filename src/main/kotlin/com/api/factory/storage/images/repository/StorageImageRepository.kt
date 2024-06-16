package com.api.factory.storage.images.repository

import com.api.factory.storage.images.dto.CreateImageLink
import com.api.factory.storage.images.models.ImageEntity
import com.api.factory.storage.images.models.ImagesTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
class StorageImageRepository : IStorageImageRepository {
    override fun addLink(image: CreateImageLink): ImageEntity {
        return ImagesTable.insertAndGetId {
            it[parentKey] = image.parentKey
            it[id] = image.image
        }.let {
            ImageEntity(it)
        }
    }

    override fun getLink(image: CreateImageLink): ImageEntity? {
        return ImageEntity.find {
            ImagesTable.parentKey eq image.parentKey
            ImagesTable.id eq image.image
        }.firstOrNull()
    }

    override fun getListImages(parentKey: UUID): List<ImageEntity?> {
        return ImageEntity.find { ImagesTable.parentKey eq parentKey }.toList()
    }

    override fun deleteLink(image: CreateImageLink): Boolean {
        return ImagesTable.deleteWhere {
            parentKey eq image.parentKey
            ImagesTable.id eq image.image
        } == 1
    }

    override fun deleteLinkAll(uuid: UUID): Boolean {
        return ImagesTable.deleteWhere {
            parentKey eq uuid
        } == 1
    }
}
