package com.api.factory.dictionary.objects.service

import com.api.factory.config.RemoveOutput
import com.api.factory.dictionary.objects.dto.ObjectInput
import com.api.factory.dictionary.objects.dto.ObjectOutput
import com.api.factory.dictionary.objects.dto.ObjectUpdateInput
import com.api.factory.dictionary.objects.repository.IObjectRepository
import com.api.factory.extensions.getResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ObjectService(override val repo: IObjectRepository) : IObjectService {
    override fun saveObject(body: ObjectInput): ObjectOutput =
        repo.saveObject(body).let {
            ObjectOutput(it.id.value, it.name)
        }

    override fun updateObject(body: ObjectUpdateInput): ObjectOutput =
        repo.updateObject(body).let {
            ObjectOutput(it.id.value, it.name)
        }

    override fun deleteObject(id: Long): RemoveOutput =
        repo.deleteObject(id).getResponse()

    override fun getAllObjects(): List<ObjectOutput> =
        repo.getAllObjects().map {
            ObjectOutput(it.id.value, it.name)
        }

    override fun getObjectById(id: Long): ObjectOutput =
        repo.getObjectById(id).let {
            ObjectOutput(it.id.value, it.name)
        }
}
