package com.kotlin.example.booklibraryservice.repository

import com.kotlin.example.booklibraryservice.dto.Author
import com.kotlin.example.booklibraryservice.dto.Book
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface LibraryRepository : MongoRepository<Book, String> {
    fun findAllByAuthorName(name: String): List<Book>
    fun findAllByAuthor(author: Author): List<Book>
}