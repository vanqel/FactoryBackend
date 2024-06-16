package com.api.factory.auth.api.handler

import com.api.factory.auth.dto.exception.HttpExceptionResponse
import com.api.factory.auth.errors.AuthError
import com.api.factory.auth.errors.CommonError
import com.api.factory.auth.errors.GeneralError
import com.api.factory.auth.errors.ValidationError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(Exception::class)
    fun handleDefaultException(exc: Exception): ResponseEntity<HttpExceptionResponse> {
        logger.error("handleDefaultException exception", exc)

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                HttpExceptionResponse(
                    msg = "Произошла ошибка",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    errors = emptyMap(),
                ),
            )
    }

    @ExceptionHandler(CommonError::class)
    fun handleCommonErrorException(exc: CommonError): ResponseEntity<HttpExceptionResponse> {
        logger.error("handleCommonErrorException ", exc)

        return ResponseEntity
            .status(exc.httpStatusFromType())
            .body(HttpExceptionResponse(exc))
    }
}

fun CommonError.httpStatusFromType(): HttpStatus {
    return when (this) {
        is ValidationError -> HttpStatus.PRECONDITION_FAILED
        is GeneralError -> HttpStatus.FORBIDDEN
        is AuthError -> HttpStatus.UNAUTHORIZED
    }
}
