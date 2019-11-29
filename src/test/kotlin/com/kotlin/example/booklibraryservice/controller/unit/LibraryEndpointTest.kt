package com.kotlin.example.booklibraryservice.controller.unit

import com.kotlin.example.booklibraryservice.controller.LibraryEndpoint
import com.kotlin.example.booklibraryservice.json.AuthorJson
import com.kotlin.example.booklibraryservice.json.BookJson
import com.kotlin.example.booklibraryservice.mapper.BookMapper
import com.kotlin.example.booklibraryservice.service.LibraryService
import com.kotlin.example.booklibraryservice.service.LibraryServiceImpl
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class LibraryEndpointTest {
    private val libraryServiceMock: LibraryServiceImpl = Mockito.mock(LibraryServiceImpl::class.java)
    private val library: LibraryEndpoint = LibraryEndpoint(libraryServiceMock)

    @Test
    @DisplayName("Should add book to the library")
    fun addBook() {
        val isbn = "123ABC"
        val title = "fly to the moon"
        val author = AuthorJson("artemas", "smith")
        val yearPublished: Long = 2004
        val bookJson = BookJson(isbn, title, author, yearPublished)
        val book = BookMapper.bookJsonToDto(bookJson)

        library.addBook(bookJson)

        verify<LibraryService?>(libraryServiceMock)?.addBook(book)
    }
}