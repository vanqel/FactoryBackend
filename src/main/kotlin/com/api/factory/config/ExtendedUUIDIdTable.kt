package com.api.factory.config

import org.jetbrains.exposed.dao.id.UUIDTable

abstract class ExtendedUUIDIdTable(name: String) : UUIDTable(name) {
    init {
        Tables.add(this)
    }
}
