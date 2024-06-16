package com.api.factory.storage.images.dto

import java.util.UUID

data class CreateImageLink(
    val parentKey: UUID,
    val image: UUID
)
