package com.api.factory.storage.core.api.http

import com.api.factory.storage.core.service.FileOutput
import com.api.factory.storage.core.service.MinioService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("api/storage")
class MinioController(
    val service: MinioService,
) {

    @GetMapping()
    fun getObject(
        @RequestParam uuid: String,
    ): FileOutput? {
        return service.getObject(UUID.fromString(uuid))
    }

    @PostMapping
    fun addObject(
        @RequestParam file: MultipartFile,
    ): FileOutput {
        return service.addObject(file)
    }

    @DeleteMapping
    fun deleteObject(
        @RequestParam uuid: String,
    ): Boolean {
        return service.delObject(uuid)
    }
}

// "019c309c-fa5a-4c5e-9329-caaa9e83c870" клюшки
// "e96d417a-5780-4c68-8190-6a58ace297c4" кхл
// "17018325-36ee-4b17-b6ee-5aad4ce8ea52" buran
