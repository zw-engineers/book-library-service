package com.kotlin.example.booklibraryservice.service

import com.kotlin.example.booklibraryservice.dto.Author
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
        val message = "The book you are updating does not exist"
        validateIfBookExists(book, message)
        libraryRepository.save(book)
    }

    override fun deleteBook(book: Book) {
        val message = "The book you are deleting does not exist"
        validateIfBookExists(book, message)
        libraryRepository.delete(book)
    }

    override fun getAllBooks(): List<Book> {
        return libraryRepository.findAll()
    }

    override fun getBooksByAuthorName(name: String): List<Book> {
        return libraryRepository.findAllByAuthorName(name)
    }

    override fun getBooksByAuthor(author: Author): List<Book> {
        return libraryRepository.findAllByAuthor(author)
    }

    private fun validateIfBookExists(book: Book, message: String) {
        libraryRepository.findById(book.isbn)
                .orElseThrow { throw BookDoesNotExistsException(message) }
    }
}