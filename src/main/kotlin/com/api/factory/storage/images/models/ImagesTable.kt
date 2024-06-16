package com.api.factory.storage.images.models

import com.api.factory.config.ExtendedUUIDIdTable


object ImagesTable: ExtendedUUIDIdTable(name = "linked_image") {
    val parentKey = uuid("parentKey")
}
