package com.api.factory.dictionary.assortment.normal.service

import com.api.factory.auth.errors.GeneralError
import com.api.factory.dictionary.assortment.models.AssortmentEntity
import com.api.factory.dictionary.assortment.normal.models.NormalEntity
import com.api.factory.dictionary.assortment.normal.dto.NormalInput
import com.api.factory.dictionary.assortment.normal.dto.NormalOutputFull

class NormalService {
    fun getAllNormal(): List<NormalOutputFull> {
       return NormalEntity.all().toList().groupBy {
            it.obj
        }.map {
            it.value.maxBy { d -> d.date }
        }.map {
            NormalOutputFull(
                objId = it.obj.toDTO(),
                count = it.count,
                date = it.date
            )
       }
    }

     fun putNormal(obj: NormalInput): NormalInput {
        val objectEnt = AssortmentEntity.findById(obj.objId)
            ?: throw GeneralError("Сортамент не найден")

        NormalEntity.new {
            this.obj = objectEnt
            this.count = obj.count
            this.date = obj.date
        }

        return obj
    }
}
