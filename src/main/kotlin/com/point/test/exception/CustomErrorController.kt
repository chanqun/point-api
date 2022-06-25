package com.point.test.exception

import com.point.test.exception.ExceptionType.NOT_FOUND
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class CustomErrorController : ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    fun error(): ErrorRes {
        return ErrorRes(error = ErrorMsg(NOT_FOUND.message, NOT_FOUND.status))
    }
}