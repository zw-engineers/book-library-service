package com.kotlin.example.booklibraryservice.web.unit

import com.kotlin.example.booklibraryservice.dto.Author
import com.kotlin.example.booklibraryservice.dto.Book
import com.kotlin.example.booklibraryservice.exception.BookDoesNotExistsException
import com.kotlin.example.booklibraryservice.exception.BookNotValidException
import com.kotlin.example.booklibraryservice.json.AuthorJson
import com.kotlin.example.booklibraryservice.json.BookJson
import com.kotlin.example.booklibraryservice.service.LibraryService
import com.kotlin.example.booklibraryservice.service.LibraryServiceImpl
import com.kotlin.example.booklibraryservice.web.LibraryEndpoint
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class LibraryEndpointTest {
    private val libraryServiceMock: LibraryServiceImpl = Mockito.mock(LibraryServiceImpl::class.java)
    private val library: LibraryEndpoint = LibraryEndpoint(libraryServiceMock)

    @Test
    fun `Should add a book to the library`() {
        val isbn = "123ABC"
        val title = "fly to the moon"
        val authorJson = AuthorJson("artemas", "smith")
        val yearPublished: Long = 2004
        val bookJson = BookJson(isbn, title, authorJson, yearPublished)
        val author = Author("artemas", "smith")
        val book = Book(isbn, title, author, yearPublished)

        library.addBook(bookJson)

        verify<LibraryService?>(libraryServiceMock)?.addBook(book)
    }

    @Test
    fun `Should throw an InvalidBook exception when A Book is not valid`() {
        val bookJson = null

        val exception = assertThrows<BookNotValidException> { library.addBook(bookJson) }

        assertThat(exception.message).isEqualTo("Book is not Valid")
    }

    @Test
    fun `Should edit an existing Book`() {
        val isbn = "123ABC"
        val title = "fly to the moon"
        val authorJson = AuthorJson("artemas", "smith")
        val yearPublished: Long = 2004
        val bookJson = BookJson(isbn, title, authorJson, yearPublished)
        val author = Author("artemas", "smith")
        val book = Book(isbn, title, author, yearPublished)

        library.editBook(bookJson)

        verify<LibraryService?>(libraryServiceMock)?.editBook(book)
    }

    @Test
    fun `Should thrown an exception when trying to edit a non existing Book`() {
        val isbn = "123ABC"
        val title = "fly to the moon"
        val authorJson = AuthorJson("artemas", "smith")
        val yearPublished: Long = 2004
        val bookJson = BookJson(isbn, title, authorJson, yearPublished)
        val author = Author("artemas", "smith")
        val book = Book(isbn, title, author, yearPublished)
        doAnswer { throw BookDoesNotExistsException("The book you are editing does not exist") }.`when`(libraryServiceMock).editBook(book)

        val exception = assertThrows<BookDoesNotExistsException> { library.editBook(bookJson) }

        assertThat(exception.message).isEqualTo("The book you are editing does not exist")
        verify<LibraryService?>(libraryServiceMock)?.editBook(book)
    }

    @Test
    fun `Should delete an existing Book`() {
        val isbn = "123ABC"
        val title = "fly to the moon"
        val authorJson = AuthorJson("artemas", "smith")
        val yearPublished: Long = 2004
        val bookJson = BookJson(isbn, title, authorJson, yearPublished)
        val author = Author("artemas", "smith")
        val book = Book(isbn, title, author, yearPublished)

        library.deleteBook(bookJson)

        verify<LibraryService?>(libraryServiceMock)?.deleteBook(book)
    }

    @Test
    fun `Should throw an exception when trying to delete a non existing Book`() {
        val isbn = "123ABC"
        val title = "fly to the moon"
        val authorJson = AuthorJson("artemas", "smith")
        val yearPublished: Long = 2004
        val bookJson = BookJson(isbn, title, authorJson, yearPublished)
        val author = Author("artemas", "smith")
        val book = Book(isbn, title, author, yearPublished)
        doAnswer { throw BookDoesNotExistsException("The book you are deleting does not exist") }.`when`(libraryServiceMock).deleteBook(book)

        val exception = assertThrows<BookDoesNotExistsException> { library.deleteBook(bookJson) }

        assertThat(exception.message).isEqualTo("The book you are deleting does not exist")
        verify<LibraryService?>(libraryServiceMock)?.deleteBook(book)
    }
}