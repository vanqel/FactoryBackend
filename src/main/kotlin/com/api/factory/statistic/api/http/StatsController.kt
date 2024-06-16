package com.api.factory.statistic.api.http

import com.api.factory.dictionary.objects.dto.ObjectOutput
import com.api.factory.statistic.dto.NormalInput
import com.api.factory.statistic.dto.StatsByTypeSum
import com.api.factory.statistic.dto.StatsObjectDayMonthYear
import com.api.factory.statistic.dto.StatsTypeOutput
import com.api.factory.statistic.service.IStatisticService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*

import java.time.LocalDate

@RestController
@RequestMapping("/api/stats")
class StatsController(override val service: IStatisticService) : IStatsController {
    @SecurityRequirement(name = "jwt")
    @GetMapping("personal/day")
    override fun getPersonalRatesByDay(
        @RequestParam date: LocalDate,
    ): List<StatsTypeOutput> {
        return service.getPersonalRatesByDay(date)
    }

    @GetMapping("personal/week")
    override fun getPersonalRatesByWeek(
        @RequestParam date: LocalDate,
    ): List<StatsTypeOutput> {
        return service.getPersonalRatesByWeek(date)
    }

    @GetMapping("personal/month")
    override fun getPersonalRatesByMonth(
        @RequestParam date: LocalDate,
    ): List<StatsTypeOutput> {
        return service.getPersonalRatesByMonth(date)
    }

    @GetMapping("departament/day")
    override fun getRatesByDayByDepartment(
        @RequestParam departId: Long,
        @RequestParam date: LocalDate,
    ): Map<ObjectOutput, List<StatsByTypeSum>> {
        return service.getRatesByDayByDepartment(departId, date)
    }

    @GetMapping("departament/week")
    override fun getRatesByWeekByDepartment(
        @RequestParam departId: Long,
        @RequestParam date: LocalDate,
    ): Map<ObjectOutput, List<StatsByTypeSum>> {
        return service.getRatesByWeekByDepartment(departId, date)
    }

    @GetMapping("departament/month")
    override fun getRatesByMonthByDepartment(
        @RequestParam departId: Long,
        @RequestParam date: LocalDate,
    ): Map<ObjectOutput, List<StatsByTypeSum>> {
        return service.getRatesByMonthByDepartment(departId, date)
    }

    @GetMapping("obects/dstamp")
    override fun getRatesByObject(
        @RequestParam objId: Long,
        @RequestParam dateStart: LocalDate,
        @RequestParam dateEnd: LocalDate,
    ): Map<ObjectOutput, List<StatsByTypeSum>> {
        return service.getRatesByObject(
            objId,
            dateStart,
            dateEnd
        )
    }

    @GetMapping("dstamp")
    override fun getRatesByDatestamp(
        @RequestParam dateStart: LocalDate,
        @RequestParam dateEnd: LocalDate,
    ): Map<ObjectOutput, List<StatsByTypeSum>> {
        return service.getRatesByDatestamp(dateStart, dateEnd)
    }

    @GetMapping()
    override fun getStatisticDayMonthTotal(
        @RequestParam date: LocalDate,
    ): Map<ObjectOutput, StatsObjectDayMonthYear> {
        return service.getStatisticDayMonthTotal(date)
    }

    @PostMapping("normal")
    override fun addNormal(body: NormalInput): NormalInput {
        return service.putNormal(body)
    }
}
