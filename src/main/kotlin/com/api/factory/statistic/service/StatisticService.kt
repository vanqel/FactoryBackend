package com.api.factory.statistic.service

import com.api.factory.auth.errors.AuthError
import com.api.factory.auth.errors.GeneralError
import com.api.factory.auth.models.department.DepartmentEntity
import com.api.factory.auth.repository.user.IUsersRepository
import com.api.factory.dictionary.objects.dto.ObjectOutput
import com.api.factory.dictionary.objects.service.IObjectService
import com.api.factory.reporting.core.dto.ReportZMKOutput
import com.api.factory.reporting.core.enums.TypeFoundation
import com.api.factory.reporting.core.models.ReportZMKEntity
import com.api.factory.reporting.core.models.ReportZMKTable
import com.api.factory.reporting.core.service.IReportService
import com.api.factory.statistic.dto.StatsByTypeSum
import com.api.factory.statistic.dto.StatsObjectDayMonthYear
import com.api.factory.statistic.dto.StatsTypeOutput
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class StatisticService(
    val usersRepository: IUsersRepository,
    val reportService: IReportService,
    val objectService: IObjectService,
) : IStatisticService {

    fun getUser() = usersRepository
        .findUserByUsername(
            SecurityContextHolder
                .getContext()
                .authentication
                .name
        ) ?: throw AuthError()

    fun getStats(
        actual: List<ReportZMKOutput>,
        prevDay: List<ReportZMKOutput>,
    ): List<StatsTypeOutput> {

        val actualStats: MutableMap<TypeFoundation, Double> =
            TypeFoundation.entries.associateWith { 0.0 }.toMutableMap()
        val prevStats: MutableMap<TypeFoundation, Double> =
            TypeFoundation.entries.associateWith { 0.0 }.toMutableMap()


        val actualStatsNormal: MutableMap<TypeFoundation, Double> =
            TypeFoundation.entries.associateWith { 0.0 }.toMutableMap()
        val prevStatsNormal: MutableMap<TypeFoundation, Double> =
            TypeFoundation.entries.associateWith { 0.0 }.toMutableMap()

        actual.groupBy {
            it.type
        }.forEach { e ->
            actualStats[e.key] = e.value.sumOf {
                it.getTotalWeight()
            }
            actualStatsNormal[e.key] = e.value.sumOf {
                it.getTotalWeightNormal()
            }
        }

        prevDay.groupBy {
            it.type
        }.forEach { e ->
            prevStats[e.key] = e.value.sumOf {
                it.getTotalWeight()
            }
            prevStatsNormal[e.key] = e.value.sumOf {
                it.getTotalWeightNormal()
            }
        }

        return TypeFoundation.entries.map {
            StatsTypeOutput(
                type = it,
                actual = actualStats[it]!!,
                prev = prevStats[it]!!,
                count = actualStats[it]!! - prevStats[it]!!,
                isGood = actualStats[it]!! >= prevStats[it]!!,
                normalToday = actualStatsNormal[it]!!,
                normalPrev = prevStatsNormal[it]!!
            )
        }
    }


    fun getStatsOne(
        actual: List<ReportZMKOutput>,
    ): List<StatsByTypeSum> {
        val conc = TypeFoundation.entries.associateWith { 0.0 }.toMutableMap()
        val normal = TypeFoundation.entries.associateWith { 0.0 }.toMutableMap()

        actual.groupBy {
            it.type
        }.forEach { e ->
            conc[e.key] = e.value.sumOf {
                it.getTotalWeight()
            }
            normal[e.key] = e.value.sumOf {
                it.getTotalWeightNormal()
            }
        }

        return TypeFoundation.entries.map {
            StatsByTypeSum(
                type = it,
                count = conc[it]!!,
                normal = normal[it]!!
            )
        }
    }

    override fun getPersonalRatesByDay(
        date: LocalDate,
    ): List<StatsTypeOutput> {
        val u = getUser()

        val today = ReportZMKEntity.find {
            ReportZMKTable.date eq date
            ReportZMKTable.user eq u.id
        }.toList().map {
            reportService.getDTOByOutput(it)
        }

        val prevDay = ReportZMKEntity.find {
            ReportZMKTable.date eq date.minusDays(1)
            ReportZMKTable.user eq u.id
        }.toList().map {
            reportService.getDTOByOutput(it)
        }
        return getStats(today, prevDay)
    }

    override fun getPersonalRatesByWeek(
        date: LocalDate,
    ): List<StatsTypeOutput> {
        val u = getUser()

        val today = ReportZMKEntity.find {
            ReportZMKTable.date less date.plusDays(1)
            ReportZMKTable.date greater date.minusWeeks(1)
            ReportZMKTable.user eq u.id
        }.toList().map {
            reportService.getDTOByOutput(it)
        }

        val prevDay = ReportZMKEntity.find {
            ReportZMKTable.date less date.minusWeeks(1).minusDays(1)
            ReportZMKTable.date greater date.minusWeeks(2)
            ReportZMKTable.user eq u.id
        }.toList().map {
            reportService.getDTOByOutput(it)
        }
        return getStats(today, prevDay)
    }

    override fun getPersonalRatesByMonth(
        date: LocalDate,
    ): List<StatsTypeOutput> {
        val u = getUser()

        val today = ReportZMKEntity.find {
            ReportZMKTable.date less date.plusDays(1)
            ReportZMKTable.date greater date.minusMonths(1)
            ReportZMKTable.user eq u.id
        }.toList().map {
            reportService.getDTOByOutput(it)
        }

        val prevDay = ReportZMKEntity.find {
            ReportZMKTable.date less date.minusMonths(1).minusDays(1)
            ReportZMKTable.date greater date.minusMonths(2)
            ReportZMKTable.user eq u.id
        }.toList().map {
            reportService.getDTOByOutput(it)
        }
        return getStats(today, prevDay)
    }

    override fun getRatesByDayByDepartment(
        departId: Long,
        date: LocalDate,
    ): Map<ObjectOutput, List<StatsByTypeSum>> {

        val today = ReportZMKEntity.find {
            ReportZMKTable.date eq date
            ReportZMKTable.department eq departId
        }.toList().map {
            reportService.getDTOByOutput(it)
        }

        val stats = today.groupBy {
            it.obj
        }.map { e ->
            e.key to getStatsOne(e.value)
        }.toMap()
        return stats
    }

    override fun getRatesByWeekByDepartment(
        departId: Long, date: LocalDate,
    ): Map<ObjectOutput, List<StatsByTypeSum>> {
        val today = ReportZMKEntity.find {
            ReportZMKTable.date less date.plusDays(1)
            ReportZMKTable.date greater date.minusWeeks(1)
            ReportZMKTable.department eq departId
        }.toList().map {
            reportService.getDTOByOutput(it)
        }

        val stats = today.groupBy {
            it.obj
        }.map { e ->
            e.key to getStatsOne(e.value)
        }.toMap()
        return stats
    }

    override fun getRatesByMonthByDepartment(
        departId: Long, date: LocalDate,
    ): Map<ObjectOutput, List<StatsByTypeSum>> {

       DepartmentEntity.findById(departId)?.let {
           if (!it.foundation){
               throw GeneralError("Отдела не является производственным")
           }
       } ?: throw GeneralError("Отдел не найден")

        val today = ReportZMKEntity.find {
            ReportZMKTable.date less date.plusDays(1)
            ReportZMKTable.date greater date.minusMonths(1)
            ReportZMKTable.department eq departId
        }.toList().map {
            reportService.getDTOByOutput(it)
        }

        val stats = today.groupBy {
            it.obj
        }.map { e ->
            e.key to getStatsOne(e.value)
        }.toMap()

        return stats
    }

    override fun getRatesByObject(
        objId: Long,
        dateStart: LocalDate,
        dateEnd: LocalDate,
    ): Map<ObjectOutput, List<StatsByTypeSum>> {
        val today = ReportZMKEntity.find {
            ReportZMKTable.obj eq objId
            ReportZMKTable.date less dateEnd
            ReportZMKTable.date greater dateStart
        }.toList().map {
            reportService.getDTOByOutput(it)
        }

        val stats = today.groupBy {
            it.obj
        }.map { e ->
            e.key to getStatsOne(e.value)
        }.toMap()

        return stats
    }

    override fun getRatesByDatestamp(
        dateStart: LocalDate,
        dateEnd: LocalDate,
    ): Map<ObjectOutput, List<StatsByTypeSum>> {
        val today = ReportZMKEntity.find {
            ReportZMKTable.date less dateEnd
            ReportZMKTable.date greater dateStart
        }.toList().map {
            reportService.getDTOByOutput(it)
        }

        val stats = today.groupBy {
            it.obj
        }.map { e ->
            e.key to getStatsOne(e.value)
        }.toMap()

        return stats
    }

    override fun getStatisticDayMonthTotal(
        date: LocalDate,
    ): Map<ObjectOutput, StatsObjectDayMonthYear> {
        val objectList = objectService.getAllObjects().associateWith {
            StatsObjectDayMonthYear(it.name, emptyList(), emptyList(), emptyList())
        }.toMutableMap()

        val today = getRatesByDatestamp(date.plusDays(1), date)
        val month = getRatesByDatestamp(date.plusDays(1), date.minusMonths(1))
        val year = getRatesByDatestamp(date.plusDays(1), date.minusYears(1))

        objectList.forEach { (t, _) ->
            objectList[t] =
                StatsObjectDayMonthYear(
                    t.name,
                    today[t] ?: emptyList(),
                    month[t] ?: emptyList(),
                    year[t] ?: emptyList()
                )
        }
        return objectList.toMap()
    }



}
