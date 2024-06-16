package com.api.factory.storage.core.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "minio")
class MinioProperties(
    @Value("\${minio.host}")
    var host: String,

    @Value("\${minio.access-key}")
    var accessKey: String,

    @Value("\${minio.secret-key}")
    var secretKey: String,

    @Value("\${minio.bucket-name}")
    var bucketName: String,

    @Value("\${minio.url}")
    var url: String,

    @Value("\${minio.max-size}")
    val maxSize: Int,
) {


    fun getLink() = "$url:$host"

}
