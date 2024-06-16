package com.api.factory.storage.images.service

import com.api.factory.storage.core.service.FileOutput
import com.api.factory.storage.images.dto.CreateImageLink
import java.util.*

interface IStorageImageService {
    fun putLink(image: CreateImageLink): FileOutput
    fun getImageByParent(parentKey: UUID): FileOutput
    fun deleteLink(image: CreateImageLink): Boolean
    fun deleteLinkAll(uuid: UUID): Boolean

}
