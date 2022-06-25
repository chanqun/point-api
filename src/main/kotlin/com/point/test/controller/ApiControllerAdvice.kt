package com.point.test.exception

import com.point.test.exception.ExceptionType.*
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
        return ErrorRes(error = ErrorMsg(BAD_REQUEST.message, BAD_REQUEST.status))
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoUserIdException::class)
    @ResponseBody
    fun noUserIdError(ex: NoUserIdException): ErrorRes {
        return ErrorRes(error = ErrorMsg(BAD_REQUEST.message, BAD_REQUEST.status))
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NotExistMembershipException::class)
    @ResponseBody
    fun notExistMembershipError(ex: NotExistMembershipException): ErrorRes {
        return ErrorRes(error = ErrorMsg(INTERNAL_SERVER_ERROR.message, INTERNAL_SERVER_ERROR.status))
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