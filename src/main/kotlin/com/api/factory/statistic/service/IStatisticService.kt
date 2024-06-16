package com.api.factory.statistic.service

import com.api.factory.dictionary.objects.dto.ObjectOutput
import com.api.factory.statistic.dto.StatsByTypeSum
import com.api.factory.statistic.dto.StatsObjectDayMonthYear
import com.api.factory.statistic.dto.StatsTypeOutput
import java.time.LocalDate

interface IStatisticService {
    fun getPersonalRatesByDay(date: LocalDate): List<StatsTypeOutput>
    fun getPersonalRatesByWeek(date: LocalDate): List<StatsTypeOutput>
    fun getPersonalRatesByMonth(date: LocalDate): List<StatsTypeOutput>

    fun getRatesByDayByDepartment(departId: Long, date: LocalDate): Map<ObjectOutput, List<StatsByTypeSum>>
    fun getRatesByWeekByDepartment(departId: Long, date: LocalDate): Map<ObjectOutput, List<StatsByTypeSum>>
    fun getRatesByMonthByDepartment(departId: Long, date: LocalDate): Map<ObjectOutput, List<StatsByTypeSum>>

    fun getRatesByObject(objId: Long, dateStart: LocalDate, dateEnd: LocalDate): Map<ObjectOutput, List<StatsByTypeSum>>
    fun getRatesByDatestamp(dateStart: LocalDate, dateEnd: LocalDate): Map<ObjectOutput, List<StatsByTypeSum>>
    fun getStatisticDayMonthTotal(date: LocalDate): Map<ObjectOutput, StatsObjectDayMonthYear>
}

