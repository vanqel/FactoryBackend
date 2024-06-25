package com.api.factory.storage.images.service

import com.api.factory.auth.errors.GeneralError
import com.api.factory.auth.errors.ValidationError
import com.api.factory.storage.core.service.FileOutput
import com.api.factory.storage.images.dto.CreateImageLink
import com.api.factory.storage.images.repository.IStorageImageRepository
import com.api.factory.storage.core.service.MinioService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class StorageImageService(
    val repo: IStorageImageRepository,
    val minioService: MinioService,
) : IStorageImageService {
    override fun putLink(image: CreateImageLink): FileOutput {
        val find = repo.getLink(image)
        return if (find == null) {
            try {
                val url = minioService.getObject(image.image)
                if (url != null) {
                    repo.addLink(image)
                    url
                } else minioService.getObject(image.image)!!
            } catch (e: Exception) {
                throw GeneralError("Ошибка загрузки изображения")
            }
        } else throw ValidationError("Изображение не найдено")

    }

    override fun getImageByParent(parentKey: UUID): FileOutput {
        return try {
            repo.getListImages(parentKey).filterNotNull().map {
                minioService.getObject(it.id.value)
            }.first() ?: run { throw ValidationError("Изображение не найдено") }
        } catch (e: Exception) {
            throw GeneralError("Ошибка загрузки изображения")
        }
    }

    override fun deleteLink(image: CreateImageLink): Boolean {
        return try {
            val url = minioService.delObject(image.image.toString())
            if (url) {
                repo.deleteLink(image)
            } else throw ValidationError("Изображение не найдено")
        } catch (e: Exception) {
            throw GeneralError("Ошибка загрузки изображения")
        }
    }

    override fun deleteLinkAll(uuid: UUID): Boolean {
        try {
            repo.getListImages(uuid).filterNotNull().forEach {
                minioService.delObject(it.id.value.toString())
            }
            return repo.deleteLinkAll(uuid)
        } catch (e: Exception) {
            throw GeneralError("Ошибка очистки изображений")
        }

    }

}
