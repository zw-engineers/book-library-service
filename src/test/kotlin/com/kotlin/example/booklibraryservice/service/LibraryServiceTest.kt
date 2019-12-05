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

    @Test
    fun `Should add book to library`() {
        val book = Book("123ABC", "fly to the moon", Author("Artemas", "Muzanenhamo"), 2008)

        libraryServiceImpl.addBook(book)

        verify(libraryRepositoryMock).save(book)
    }

    @Test
    fun `Should update an existing book`() {
        val book = Book("123ABC", "fly to the moon", Author("Artemas", "Muzanenhamo"), 2008)
        `when`(libraryRepositoryMock.findById(book.isbn)).thenReturn(Optional.of(book))

        libraryServiceImpl.editBook(book)

        verify(libraryRepositoryMock).save(book)
    }

    @Test
    fun `Should throw an exception when updating a book that does not exist`() {
        val book = Book("789XYZ", "fly to the moon", Author("Artemas", "Muzanenhamo"), 2008)
        `when`(libraryRepositoryMock.findById(book.isbn)).thenReturn(Optional.empty())

        val exception = assertThrows<BookDoesNotExistsException> { libraryServiceImpl.editBook(book) }

        assertThat(exception.message).isEqualTo("The book you are updating does not exist")
    }
}