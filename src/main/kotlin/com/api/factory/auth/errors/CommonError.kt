package com.api.factory.auth.errors

import com.api.factory.auth.exceptions.IExError

sealed class CommonError(
    override val message: String,
    override val errorType: ErrorType,
    override val errors: Map<String, String>,
) : IExError, RuntimeException()
