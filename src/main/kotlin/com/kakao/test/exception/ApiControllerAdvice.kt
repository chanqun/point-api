package com.kakao.test.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun processValidationError(ex: MethodArgumentNotValidException): ErrorRes {
        return ErrorRes(error = ErrorMsg(ExceptionType.BAD_REQUEST.message, ExceptionType.BAD_REQUEST.status))
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoUserIdException::class)
    @ResponseBody
    fun noUserIdError(ex: NoUserIdException): ErrorRes {
        return ErrorRes(error = ErrorMsg(ExceptionType.BAD_REQUEST.message, ExceptionType.BAD_REQUEST.status))
    }
}

data class ErrorRes(
    val success: Boolean = false,
    val response: String? = null,
    val error: ErrorMsg
)

data class ErrorMsg(
    val message: String,
    val status: Int
)