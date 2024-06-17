package com.api.factory.statistic.dto

import com.api.factory.reporting.core.enums.TypeFoundation

data class StatsByTypeSum(
    val type: TypeFoundation,
    val count: Double,
    val normal: Double
){
    fun getDelta() = count - normal
    fun getDeltaPercent() =
        if(getDelta() == 0.0) 0.0
        else 100.0 - getDelta() / if(normal == 0.0) 0.000001 else normal
    fun getPositive() = if(getDelta() > 0) "Выполнена" else "Не выполнена"
}
