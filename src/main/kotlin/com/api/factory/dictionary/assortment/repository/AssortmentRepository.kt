package com.api.factory.dictionary.assortment.repository

import com.api.factory.auth.errors.GeneralError
import com.api.factory.dictionary.assortment.dto.AssortmentInput
import com.api.factory.dictionary.assortment.dto.AssortmentUpdateInput
import com.api.factory.dictionary.assortment.models.AssortmentEntity
import com.api.factory.dictionary.assortment.models.AssortmentTable
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class AssortmentRepository : IAssortmentRepository {
    override fun saveAssortment(body: AssortmentInput): AssortmentEntity =
        AssortmentEntity.new {
            name = body.name
            count = body.count
        }


    override fun updateAssortment(body: AssortmentUpdateInput): AssortmentEntity =
        AssortmentEntity.findByIdAndUpdate(body.id) {
            it.name = body.name
            it.count = body.count
        }?: throw GeneralError("Сортамент не найден")


    override fun deleteAssortment(id: Long): Boolean =
        AssortmentEntity.findByIdAndUpdate(id) {
            it.archived = true
        }?.let { true } ?: throw GeneralError("Сортамент не найден")


    override fun getAllAssortment(): List<AssortmentEntity> =
        AssortmentEntity.find {
            AssortmentTable.archived eq false
        }.toList()

    override fun getAssortmentById(id: Long): AssortmentEntity =
        AssortmentEntity.findById(id) ?: throw GeneralError("Сортамент не найден")

}
