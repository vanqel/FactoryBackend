package com.api.factory.dictionary.assortment.service

import com.api.factory.config.RemoveOutput
import com.api.factory.dictionary.assortment.dto.AssortmentInput
import com.api.factory.dictionary.assortment.dto.AssortmentOutput
import com.api.factory.dictionary.assortment.dto.AssortmentUpdateInput
import com.api.factory.dictionary.assortment.repository.IAssortmentRepository

interface IAssortmentService {
    val repo: IAssortmentRepository

    fun saveAssortment(body: AssortmentInput): AssortmentOutput
    fun updateAssortment(body: AssortmentUpdateInput): AssortmentOutput
    fun deleteAssortment(id: Long): RemoveOutput
    fun getAllAssortment(): List<AssortmentOutput>
    fun getAssortmentById(id: Long): AssortmentOutput
}
