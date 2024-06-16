package com.api.factory.reporting.core.dto

import com.api.factory.auth.dto.department.DepartmentOutput
import com.api.factory.auth.dto.users.UserOutput
import com.api.factory.dictionary.assortment.dto.AssortmentOutput
import com.api.factory.dictionary.objects.dto.ObjectOutput
import com.api.factory.reporting.core.enums.TypeFoundation
import java.time.LocalDate

data class ReportZMKOutput(
    val id: Long,
    val user: UserOutput?,
    val department: DepartmentOutput?,
    val obj: ObjectOutput,
    val assortment: AssortmentOutput,
    val type: TypeFoundation,
    val date: LocalDate,
    val count: Double
)
