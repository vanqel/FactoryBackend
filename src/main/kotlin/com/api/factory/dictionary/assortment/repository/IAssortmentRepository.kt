package com.api.factory.dictionary.assortment.repository

import com.api.factory.dictionary.assortment.dto.AssortmentInput
import com.api.factory.dictionary.assortment.dto.AssortmentUpdateInput
import com.api.factory.dictionary.assortment.models.AssortmentEntity

interface IAssortmentRepository {
    fun saveAssortment(body: AssortmentInput): AssortmentEntity
    fun updateAssortment(body: AssortmentUpdateInput): AssortmentEntity
    fun deleteAssortment(id: Long): Boolean
    fun getAllAssortment(): List<AssortmentEntity>
    fun getAssortmentById(id: Long): AssortmentEntity
}
