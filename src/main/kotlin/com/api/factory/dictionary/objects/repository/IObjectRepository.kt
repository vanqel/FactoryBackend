package com.api.factory.dictionary.objects.repository

import com.api.factory.dictionary.objects.dto.ObjectInput
import com.api.factory.dictionary.objects.dto.ObjectUpdateInput
import com.api.factory.dictionary.objects.models.ObjectEntity

interface IObjectRepository {
    fun saveObject(body: ObjectInput): ObjectEntity
    fun updateObject(body: ObjectUpdateInput): ObjectEntity
    fun deleteObject(id: Long): Boolean
    fun getAllObjects(): List<ObjectEntity>
    fun getObjectById(id: Long): ObjectEntity
}





