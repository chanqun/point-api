package com.kakao.test.exception

enum class ExceptionType(val status: Int, val message: String) {

    BAD_REQUEST(400, "X-USER-ID나 요청 데이터를 확인해주세요"),
    NOT_FOUND(404, "요청한 주소나 데이터가 없습니다."),

    INTERNAL_SERVER_ERROR(500, "알 수 없는 서버에러")
}