package com.kotlin.example.booklibraryservice.service

import com.kotlin.example.booklibraryservice.dto.Book

interface LibraryService {
    fun addBook(book: Book)
    fun editBook(book: Book)
    fun deleteBook(book: Book)
    fun getAllBooks(): List<Book>
    fun getBookByAuthorName(name: String): List<Book>
}