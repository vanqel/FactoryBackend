package com.api.factory.extensions

import com.api.factory.config.ExtendedLongIdTable
import org.jetbrains.exposed.sql.*

fun SizedIterable<*>.exists() = this.empty().not()

fun ExtendedLongIdTable.selectAllNotDeleted(): Query =
    Query(this, null)
        .andWhere { this@selectAllNotDeleted.deletedAt eq null }
