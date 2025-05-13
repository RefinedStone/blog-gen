package com.akradev.bloggen

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogGenApplication

fun main(args: Array<String>) {
    runApplication<BlogGenApplication>(*args)
}
