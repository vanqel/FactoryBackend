package com.api.factory.storage.core.service

import com.api.factory.storage.core.config.MinioProperties
import io.minio.*
import io.minio.http.Method
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class MinioService(
    val client: MinioClient,
    val property: MinioProperties,
) {

    fun getObject(name: UUID): FileOutput? {
        val url = client.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(property.bucketName)
                .`object`(name.toString())
                .expiry(1, TimeUnit.HOURS)
                .build()
        )
        return FileOutput(url, name)
    }

    fun delObject(name: String): Boolean {
        return try {
            client.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(property.bucketName)
                    .`object`(name)
                    .build(),
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    fun updateObject(name: String, obj: MultipartFile): String {

        client.putObject(
            PutObjectArgs.builder()
                .bucket(property.bucketName)
                .`object`(name)
                .stream(obj.inputStream, obj.size, -1)
                .contentType(obj.contentType)
                .build()
        )
        return "asd"
    }

    fun addObject(obj: MultipartFile): FileOutput {
        val name = UUID.randomUUID()

        client.putObject(
            PutObjectArgs.builder()
                .bucket(property.bucketName)
                .`object`("$name")
                .stream(obj.inputStream, obj.size, -1)
                .contentType(obj.contentType)
                .build()
        )

        return getObject(name)!!
    }
}

data class FileOutput(
    val url: String?,
    val uuid: UUID,
)
