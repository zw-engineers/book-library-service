package com.kotlin.example.booklibraryservice.service

import com.kotlin.example.booklibraryservice.dto.Book
import com.kotlin.example.booklibraryservice.exception.BookDoesNotExistsException
import com.kotlin.example.booklibraryservice.repository.LibraryRepository
import org.springframework.stereotype.Service

@Service
class LibraryServiceImpl(val libraryRepository: LibraryRepository) : LibraryService {
    override fun addBook(book: Book) {
        libraryRepository.save(book)
    }

    override fun editBook(book: Book) {
        libraryRepository.findById(book.isbn)
                .orElseThrow { throw BookDoesNotExistsException("The book you are updating does not exist") }

        libraryRepository.save(book)
    }

    override fun deleteBook(book: Book) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}