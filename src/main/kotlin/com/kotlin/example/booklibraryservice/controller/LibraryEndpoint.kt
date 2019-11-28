package com.kotlin.example.booklibraryservice.controller

import com.kotlin.example.booklibraryservice.json.BookJson
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LibraryEndpoint {

    @PostMapping("/book")
    fun addBook(@RequestBody book: BookJson) {
    }
}