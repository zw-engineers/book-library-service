package com.kotlin.example.booklibraryservice.service

import com.kotlin.example.booklibraryservice.dto.Book
import com.kotlin.example.booklibraryservice.repository.LibraryRepository
import org.springframework.stereotype.Service

@Service
class LibraryServiceImpl(val libraryRepository: LibraryRepository) : LibraryService {
    override fun addBook(book: Book) {
        libraryRepository.save(book)
    }
}