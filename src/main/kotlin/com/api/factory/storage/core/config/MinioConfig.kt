package com.api.factory.storage.core.config

import io.minio.*
import org.apache.logging.log4j.kotlin.logger
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(MinioProperties::class)
class MinioConfig(
    val minioProperties: MinioProperties,
) {

    @Bean
    fun getMinio(): MinioClient {
        logger.info("MINIO - Connection to ${minioProperties.getLink()}..")
        return MinioClient.builder()
            .endpoint(minioProperties.getLink())
            .credentials(
                minioProperties.accessKey,
                minioProperties.secretKey
            ).build().let {
                logger.info("MINIO - Connection to ${minioProperties.getLink()}..OK")
                it
            }
    }


    @Bean
    fun setBucket(): String {
        if (!getMinio().bucketExists(BucketExistsArgs.builder().bucket(minioProperties.bucketName).build())) {
            logger.info("Create bucket ${minioProperties.bucketName}")
            getMinio().makeBucket(MakeBucketArgs.builder().bucket(minioProperties.bucketName).build())
        } else logger.info("Bucket ${minioProperties.bucketName} loaded")

        return "generate bucket ${minioProperties.bucketName}"
    }
}
