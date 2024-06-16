package com.api.factory.dictionary.objects.repository

import com.api.factory.auth.errors.GeneralError
import com.api.factory.dictionary.objects.dto.ObjectInput
import com.api.factory.dictionary.objects.dto.ObjectUpdateInput
import com.api.factory.dictionary.objects.models.ObjectEntity
import com.api.factory.dictionary.objects.models.ObjectsTable

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
@Transactional
class ObjectRepository : IObjectRepository {
    override fun saveObject(body: ObjectInput): ObjectEntity =
        ObjectEntity.new {
            name = body.name
        }


    override fun updateObject(body: ObjectUpdateInput): ObjectEntity =
        ObjectEntity.findByIdAndUpdate(body.id) {
            it.name = body.name
        } ?: throw GeneralError("Сортамент не найден")


    override fun deleteObject(id: Long): Boolean =
        ObjectEntity.findByIdAndUpdate(id) {
            it.archived = true
        }?.let { true } ?: throw GeneralError("Сортамент не найден")


    override fun getAllObjects(): List<ObjectEntity> =
        ObjectEntity.find {
            ObjectsTable.archived eq false
        }.toList()

    override fun getObjectById(id: Long): ObjectEntity =
        ObjectEntity.findById(id) ?: throw GeneralError("Сортамент не найден")

}
