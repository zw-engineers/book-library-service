package com.kotlin.example.booklibraryservice.service

import com.kotlin.example.booklibraryservice.dto.Author
import com.kotlin.example.booklibraryservice.dto.Book
import com.kotlin.example.booklibraryservice.exception.BookDoesNotExistsException
import com.kotlin.example.booklibraryservice.repository.LibraryRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class LibraryServiceTest {

    private val libraryRepositoryMock = mock(LibraryRepository::class.java)
    private val libraryServiceImpl = LibraryServiceImpl(libraryRepositoryMock)
    private val isbn = "123ABC"
    private val title = "fly to the moon"
    private val author = Author("Artemas", "Muzanenhamo")
    private val yearPublished: Long = 2008
    private val book: Book = Book(isbn, title, author, yearPublished)

    @Test
    fun `Should add book to library`() {

        libraryServiceImpl.addBook(book)

        verify(libraryRepositoryMock).save(book)
    }

    @Test
    fun `Should update an existing book`() {
        `when`(libraryRepositoryMock.findById(isbn)).thenReturn(Optional.of(book))

        libraryServiceImpl.editBook(book)

        verify(libraryRepositoryMock).findById(isbn)
        verify(libraryRepositoryMock).save(book)
    }

    @Test
    fun `Should throw an exception when updating a book that does not exist`() {
        `when`(libraryRepositoryMock.findById(book.isbn)).thenReturn(Optional.empty())

        val exception = assertThrows<BookDoesNotExistsException> { libraryServiceImpl.editBook(book) }

        assertThat(exception.message).isEqualTo("The book you are updating does not exist")
        verify(libraryRepositoryMock).findById(isbn)
        verify(libraryRepositoryMock, never()).delete(book)
    }

    @Test
    fun `Should delete an existing book`() {
        `when`(libraryRepositoryMock.findById(isbn)).thenReturn(Optional.of(book))

        libraryServiceImpl.deleteBook(book)

        verify(libraryRepositoryMock).findById(isbn)
        verify(libraryRepositoryMock).delete(book)
    }

    @Test
    fun `Should thrown an exception when deleting a book that does not exist`() {
        `when`(libraryRepositoryMock.findById(isbn)).thenReturn(Optional.empty())

        val exception = assertThrows<BookDoesNotExistsException> { libraryServiceImpl.deleteBook(book) }

        assertThat(exception.message).isEqualTo("The book you are deleting does not exist")
        verify(libraryRepositoryMock).findById(isbn)
        verify(libraryRepositoryMock, never()).delete(book)
    }
}