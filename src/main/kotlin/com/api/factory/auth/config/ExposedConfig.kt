package com.api.factory.auth.config

import org.jetbrains.exposed.spring.DatabaseInitializer
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.ExperimentalKeywordApi
import org.jetbrains.exposed.sql.Schema
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
class ExposedConfig(
    private val applicationContext: ApplicationContext,
) {
    @Bean
    @OptIn(ExperimentalKeywordApi::class)
    fun databaseConfig() =
        DatabaseConfig {
            useNestedTransactions = false
            defaultSchema = Schema(name = "public")
            preserveKeywordCasing = true
        }

    @Bean
    fun databaseInitializer() =
        DatabaseInitializer(
            applicationContext = applicationContext,
            excludedPackages = listOf(),
        )
}

