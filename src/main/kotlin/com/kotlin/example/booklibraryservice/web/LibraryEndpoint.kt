package com.kotlin.example.booklibraryservice.web

import com.kotlin.example.booklibraryservice.json.BookJson
import com.kotlin.example.booklibraryservice.mapper.BookMapper
import com.kotlin.example.booklibraryservice.service.LibraryService
import org.springframework.web.bind.annotation.*

@RestController
class LibraryEndpoint(private val libraryService: LibraryService) {

    @PostMapping("/book")
    fun addBook(@RequestBody bookJson: BookJson?) {
        val book = BookMapper.bookJsonToDto(bookJson)
        libraryService.addBook(book)
    }

    @PostMapping("/book/edit")
    fun editBook(@RequestBody bookJson: BookJson?) {
        val book = BookMapper.bookJsonToDto(bookJson)
        libraryService.editBook(book)
    }

    @DeleteMapping("/book/remove")
    fun deleteBook(@RequestBody bookJson: BookJson?) {
        val book = BookMapper.bookJsonToDto(bookJson)
        libraryService.deleteBook(book)
    }

    @GetMapping("/books")
    fun getAllBooks(): List<BookJson> {
        val books = libraryService.getAllBooks()
        return BookMapper.booksDtoToJson(books)
    }
}