package com.kakao.test.exception

enum class ExceptionType(val status: Int, val message: String) {

    BAD_REQUEST(400, "잘못된 요청입니다."),
    NOT_FOUND(404, "요청한 주소나 데이터가 없습니다."),

    INTERNAL_SERVICE_ERROR(500, "알 수 없는 서버에러")
}