package com.api.factory.reporting.core.dto

import com.api.factory.auth.dto.department.DepartmentOutput
import com.api.factory.auth.dto.users.UserOutput
import com.api.factory.dictionary.assortment.dto.AssortmentOutput
import com.api.factory.dictionary.objects.dto.ObjectOutput
import com.api.factory.reporting.core.enums.TypeFoundation
import com.api.factory.storage.core.service.FileOutput
import java.time.LocalDate

data class ReportZMKOutput(
    val id: Long,
    val user: UserOutput?,
    val department: DepartmentOutput?,
    val obj: ObjectOutput,
    val assortment: AssortmentOutput,
    val type: TypeFoundation,
    val date: LocalDate,
    val count: Long,
    val normal: Long = 1,
    val image: FileOutput?
) {
    fun getTotalWeight() = count*assortment.count

    fun getTotalWeightNormal() = normal*assortment.count
    
    fun getDelta() = getTotalWeight() - getTotalWeightNormal()

    fun getDeltaPercent() =
        if(getDelta() == 0.0) 0.0
        else 100* getTotalWeight()  / if(getTotalWeightNormal() == 0.0) getTotalWeight() else getTotalWeightNormal()

    fun getPositive() = if(getDelta() > 0) "Выполнена" else "Не выполнена"
}
