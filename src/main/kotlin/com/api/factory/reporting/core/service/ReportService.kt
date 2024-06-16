package com.api.factory.reporting.core.service

import com.api.factory.auth.errors.AuthError
import com.api.factory.auth.errors.GeneralError
import com.api.factory.auth.models.users.UserEntity
import com.api.factory.auth.repository.user.IUsersRepository
import com.api.factory.config.RemoveOutput
import com.api.factory.dictionary.assortment.repository.IAssortmentRepository
import com.api.factory.dictionary.objects.repository.IObjectRepository
import com.api.factory.extensions.getResponse
import com.api.factory.reporting.core.dto.ReportZMKCreateInput
import com.api.factory.reporting.core.dto.ReportZMKOutput
import com.api.factory.reporting.core.dto.ReportZMKUpdateInput
import com.api.factory.reporting.core.models.ReportZMKEntity
import com.api.factory.reporting.core.models.ReportZMKTable
import com.api.factory.storage.images.dto.CreateImageLink
import com.api.factory.storage.images.service.IStorageImageService
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
class ReportService(
    val usersRepository: IUsersRepository,
    val assortRepository: IAssortmentRepository,
    val objectsRepository: IObjectRepository,
    val imageService: IStorageImageService,
) : IReportService {

    fun getUser() =
        usersRepository
            .findUserByUsername(
                SecurityContextHolder
                    .getContext()
                    .authentication
                    .name
            ) ?: throw AuthError()


    override fun save(inputReport: ReportZMKCreateInput): ReportZMKOutput {

        imageService.putLink(
            CreateImageLink(
                parentKey = UUID.fromString(inputReport.image),
                image = UUID.fromString(inputReport.keyImage)
            )
        )
        val u = getUser()
        val o = objectsRepository.getObjectById(inputReport.obj)
        val a = assortRepository.getAssortmentById(inputReport.assortment)

        val report = runCatching {
            ReportZMKEntity.new {
                user = u.id
                department = u.department.first().id
                obj = o.id
                assortment = a.id
                date = inputReport.date
                count = inputReport.count
                img = UUID.fromString(inputReport.keyImage)
                type = inputReport.type
            }
        }.getOrElse {
            imageService.deleteLinkAll(UUID.fromString(inputReport.keyImage))
            throw it
        }
        return ReportZMKOutput(
            report.id.value,
            u.toDTO(),
            u.toDTO().department,
            o.toDTO(),
            a.toDTO(),
            report.type,
            report.date,
            report.count
        )


    }

    override fun getDTOByOutput(report: ReportZMKEntity): ReportZMKOutput {

        val u = UserEntity.findById(report.user)
        val o = objectsRepository.getObjectById(report.obj.value)
        val a = assortRepository.getAssortmentById(report.assortment.value)

        return ReportZMKOutput(
            report.id.value,
            u?.toDTO(),
            u?.toDTO()?.department,
            o.toDTO(),
            a.toDTO(),
            report.type,
            report.date,
            report.count
        )
    }

    override fun getById(id: Long): ReportZMKOutput {
        val report = ReportZMKEntity.findById(id) ?: throw GeneralError("Отчет не найден")
        return getDTOByOutput(report)
    }

    override fun update(id: Long, inputReport: ReportZMKUpdateInput): ReportZMKOutput {
        val o = objectsRepository.getObjectById(inputReport.obj)
        val a = assortRepository.getAssortmentById(inputReport.assortment)

        val report = ReportZMKEntity.findByIdAndUpdate(id) {
            it.obj = o.id
            it.count = inputReport.count
            it.assortment = a.id
            it.obj = o.id
            it.type = inputReport.type
        } ?: throw GeneralError("Отчет не найден")

        imageService.deleteLinkAll(report.img)
        imageService.putLink(CreateImageLink(report.img,UUID.fromString(inputReport.image)))

        return getDTOByOutput(report)
    }

    override fun delete(id: Long): RemoveOutput {
        return (ReportZMKTable.deleteWhere { ReportZMKTable.id eq id } == 1).getResponse()
    }

    override fun getAll(): List<ReportZMKOutput> {
        return ReportZMKEntity.all().map {
            getDTOByOutput(it)
        }
    }
}
