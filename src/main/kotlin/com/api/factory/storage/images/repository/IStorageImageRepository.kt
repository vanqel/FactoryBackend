package com.api.factory.storage.images.repository

import com.api.factory.storage.images.dto.CreateImageLink
import com.api.factory.storage.images.models.ImageEntity
import java.util.UUID

interface IStorageImageRepository {
    fun addLink(image: CreateImageLink): ImageEntity

    fun getLink(image: CreateImageLink): ImageEntity?

    fun getListImages(parentKey: UUID): List<ImageEntity?>

    fun deleteLink(image: CreateImageLink): Boolean

    fun deleteLinkAll(uuid: UUID): Boolean

}
