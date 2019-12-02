package com.kotlin.example.booklibraryservice.controller

import com.kotlin.example.booklibraryservice.json.BookJson
import com.kotlin.example.booklibraryservice.mapper.BookMapper
import com.kotlin.example.booklibraryservice.service.LibraryService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LibraryEndpoint(private val libraryService: LibraryService) {

    @PostMapping("/book")
    fun addBook(@RequestBody bookJson: BookJson?) {
        val book = BookMapper.bookJsonToDto(bookJson)
        libraryService.addBook(book)
    }
}