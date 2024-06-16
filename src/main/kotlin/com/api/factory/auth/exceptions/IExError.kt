package com.api.factory.auth.exceptions

import com.api.factory.auth.errors.ErrorType

interface IExError {
    val message: String
    val errorType: ErrorType
    val errors: Map<String, String>

    fun hasErrors(): Boolean {
        return errors.isNotEmpty()
    }
}
