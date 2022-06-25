package com.point.test

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PointApiApplication

fun main(args: Array<String>) {
    runApplication<PointApiApplication>(*args)
}
