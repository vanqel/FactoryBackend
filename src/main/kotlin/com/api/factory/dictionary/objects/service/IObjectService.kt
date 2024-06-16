package com.api.factory.dictionary.objects.service

import com.api.factory.config.RemoveOutput
import com.api.factory.dictionary.objects.dto.ObjectInput
import com.api.factory.dictionary.objects.dto.ObjectOutput
import com.api.factory.dictionary.objects.dto.ObjectUpdateInput
import com.api.factory.dictionary.objects.repository.IObjectRepository

interface IObjectService {
    val repo: IObjectRepository
    fun saveObject(body: ObjectInput): ObjectOutput
    fun updateObject(body: ObjectUpdateInput): ObjectOutput
    fun deleteObject(id: Long): RemoveOutput
    fun getAllObjects(): List<ObjectOutput>
    fun getObjectById(id: Long): ObjectOutput
}





