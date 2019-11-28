package com.kotlin.example.booklibraryservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookLibraryServiceApplication

fun main(args: Array<String>) {
	runApplication<BookLibraryServiceApplication>(*args)
}
