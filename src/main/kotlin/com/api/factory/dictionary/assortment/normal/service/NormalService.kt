package com.api.factory.dictionary.assortment.normal.service

import com.api.factory.auth.errors.GeneralError
import com.api.factory.dictionary.assortment.models.AssortmentEntity
import com.api.factory.dictionary.assortment.normal.models.NormalEntity
import com.api.factory.dictionary.assortment.normal.dto.NormalInput
import com.api.factory.dictionary.assortment.normal.dto.NormalOutputFull
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
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

    fun putNormal(obj: NormalInput): NormalOutputFull {
        val objectEnt = AssortmentEntity.findById(obj.objId)
            ?: throw GeneralError("Сортамент не найден")

        val n = NormalEntity.new {
            this.obj = objectEnt
            this.count = obj.count
            this.date = LocalDate.now()
        }

        return n.let {
            NormalOutputFull(
                objId = it.obj.toDTO(),
                count = it.count,
                date = it.date
            )
        }
    }
}
