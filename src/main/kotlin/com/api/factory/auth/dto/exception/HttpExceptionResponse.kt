package com.api.factory.auth.dto.exception

import com.api.factory.auth.api.handler.httpStatusFromType
import com.api.factory.auth.errors.CommonError
import org.springframework.http.HttpStatus

data class HttpExceptionResponse(
    val msg: String,
    val httpStatus: HttpStatus,
    val errors: Map<String, Any> = mapOf(),
) {
    constructor(commonError: CommonError) : this(
        msg = commonError.localizedMessage,
        httpStatus = commonError.httpStatusFromType(),
        errors = commonError.errors,
    )
}
