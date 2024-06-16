package com.api.factory.auth.models.roles.table

object ConstantRoles {
    const val ADMIN = "ADMIN"
    const val DIMK = "Руководитель ДИМК"
    const val N_PLACE = "Начальник участка"
    const val N_EXPORT = "Прораб по отгрузке"
}

enum class RolesEnum(
    val id: Long,
    val constantRole: String,
) {
    ADMIN(id = 1, constantRole = ConstantRoles.ADMIN),
    DIMK(id = 2, constantRole = ConstantRoles.DIMK),
    N_PLACE(id = 3, constantRole = ConstantRoles.N_PLACE),
    N_EXPORT(id = 4, constantRole = ConstantRoles.N_EXPORT),

}
