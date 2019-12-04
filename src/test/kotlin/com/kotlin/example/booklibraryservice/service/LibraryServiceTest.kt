package com.kotlin.example.booklibraryservice.service

import com.kotlin.example.booklibraryservice.dto.Author
import com.kotlin.example.booklibraryservice.dto.Book
import com.kotlin.example.booklibraryservice.repository.LibraryRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

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
}