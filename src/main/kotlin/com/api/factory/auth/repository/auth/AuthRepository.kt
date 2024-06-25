package com.api.factory.auth.repository.auth

import com.api.factory.auth.config.SecurityProperties
import com.api.factory.auth.dto.authorization.AuthInput
import com.api.factory.auth.dto.authorization.AuthOutput
import com.api.factory.auth.models.authorites.UserLoginEntity
import com.api.factory.auth.models.authorites.table.UserLoginTable
import com.api.factory.extensions.exists
import com.api.factory.extensions.selectAllNotDeleted
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException

@Repository
@Transactional
class AuthRepository: IAuthRepository {
    override fun save(inputAuth: AuthInput): AuthOutput? {
//        if (UserLoginEntity.find { UserLoginTable.user eq inputAuth.userEntity.id }.exists()) {
//            UserLoginTable.deleteWhere { user eq inputAuth.userEntity.id.value }
//        }
        UserLoginTable.insertAndGetId {
            it[user] = inputAuth.userEntity.id
            it[accesstoken] = inputAuth.accessToken
            it[refreshtoken] = inputAuth.refreshToken
        }
        return AuthOutput(inputAuth.userEntity.id.value, inputAuth.accessToken, inputAuth.refreshToken)
    }

    override fun deleteByAccessToken(token: String): Boolean {
        val tokenEdited = token.substringAfter(SecurityProperties.TOKEN_PREFIX_ACCESS)
        return try {
            UserLoginTable.deleteWhere { accesstoken.eq(tokenEdited) }
            true
        } catch (e: SQLException) {
            false
        }
    }

    override fun existAccessToken(token: String): Boolean =
        UserLoginTable.selectAllNotDeleted()
            .andWhere { UserLoginTable.accesstoken eq token }
            .exists()

    override fun existRefreshToken(token: String): Boolean =
        UserLoginTable.selectAllNotDeleted()
            .andWhere { UserLoginTable.refreshtoken eq token }
            .exists()

    override fun findAuthByRefreshToken(refreshToken: String): UserLoginEntity? =
        UserLoginEntity.find { UserLoginTable.refreshtoken eq refreshToken }.firstOrNull()
}
