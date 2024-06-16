package com.api.factory.dictionary.assortment.service

import com.api.factory.config.RemoveOutput
import com.api.factory.dictionary.assortment.dto.AssortmentInput
import com.api.factory.dictionary.assortment.dto.AssortmentOutput
import com.api.factory.dictionary.assortment.dto.AssortmentUpdateInput
import com.api.factory.dictionary.assortment.repository.IAssortmentRepository
import com.api.factory.extensions.getResponse
import org.springframework.stereotype.Service

@Service
class AssortmentService(override val repo: IAssortmentRepository) : IAssortmentService {
    override fun saveAssortment(body: AssortmentInput): AssortmentOutput =
        repo.saveAssortment(body).let {
            AssortmentOutput(it.id.value, it.name, it.count)
        }

    override fun updateAssortment(body: AssortmentUpdateInput): AssortmentOutput =
        repo.updateAssortment(body).let {
            AssortmentOutput(it.id.value, it.name, it.count)
        }

    override fun deleteAssortment(id: Long): RemoveOutput =
        repo.deleteAssortment(id).getResponse()

    override fun getAllAssortment(): List<AssortmentOutput> =
        repo.getAllAssortment().map {
            AssortmentOutput(it.id.value, it.name, it.count)
        }

    override fun getAssortmentById(id: Long): AssortmentOutput =
        repo.getAssortmentById(id).let {
            AssortmentOutput(it.id.value, it.name, it.count)
        }
}
