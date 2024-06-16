package com.api.factory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GameStoreBackendKotlinApplication

fun main(args: Array<String>) {
    runApplication<GameStoreBackendKotlinApplication>(*args)
}
