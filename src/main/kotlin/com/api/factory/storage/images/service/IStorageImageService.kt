package com.api.factory.storage.images.service

import com.api.factory.storage.images.dto.CreateImageLink
import java.util.*

interface IStorageImageService {
    fun putLink(image: CreateImageLink): String
    fun getListImages(parentKey: UUID): List<String?>
    fun deleteLink(image: CreateImageLink): Boolean
    fun deleteLinkAll(uuid: UUID): Boolean

}
