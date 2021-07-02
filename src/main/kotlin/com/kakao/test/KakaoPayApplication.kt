package com.kakao.test

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KakaoPayApplication

fun main(args: Array<String>) {
    runApplication<KakaoPayApplication>(*args)
}
