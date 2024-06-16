package com.api.factory.extensions

import com.api.factory.auth.errors.CommonError
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

typealias Result<T> = Result<T, CommonError>

fun <T> Result.Companion.ok(value: T) = Ok(value)

fun <E : CommonError> Result.Companion.error(err: E) = Err(err)

