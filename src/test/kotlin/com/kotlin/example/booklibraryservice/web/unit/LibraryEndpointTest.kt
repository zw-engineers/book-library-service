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
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class LibraryEndpointTest {
    private val libraryServiceMock: LibraryServiceImpl = mock(LibraryServiceImpl::class.java)
    private val library: LibraryEndpoint = LibraryEndpoint(libraryServiceMock)

    private val isbn = "123ABC"
    private val title = "fly to the moon"
    private val authorJson = AuthorJson("artemas", "smith")
    private val yearPublished: Long = 2004

    @Test
    fun `Should add a book to the library`() {
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
        val bookJson = BookJson(isbn, title, authorJson, yearPublished)
        val author = Author("artemas", "smith")
        val book = Book(isbn, title, author, yearPublished)

        library.editBook(bookJson)

        verify<LibraryService?>(libraryServiceMock)?.editBook(book)
    }

    @Test
    fun `Should thrown an exception when trying to edit a non existing Book`() {
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
        val bookJson = BookJson(isbn, title, authorJson, yearPublished)
        val author = Author("artemas", "smith")
        val book = Book(isbn, title, author, yearPublished)

        library.deleteBook(bookJson)

        verify<LibraryService?>(libraryServiceMock)?.deleteBook(book)
    }

    @Test
    fun `Should throw an exception when trying to delete a non existing Book`() {
        val bookJson = BookJson(isbn, title, authorJson, yearPublished)
        val author = Author("artemas", "smith")
        val book = Book(isbn, title, author, yearPublished)
        doAnswer { throw BookDoesNotExistsException("The book you are deleting does not exist") }.`when`(libraryServiceMock).deleteBook(book)

        val exception = assertThrows<BookDoesNotExistsException> { library.deleteBook(bookJson) }

        assertThat(exception.message).isEqualTo("The book you are deleting does not exist")
        verify<LibraryService?>(libraryServiceMock)?.deleteBook(book)
    }

    @Test
    fun `Should return all books in the library`() {
        val isbn = "123ABC"
        val title = "fly to the moon"
        val name = "artemas"
        val surname = "smith"
        val authorJson = AuthorJson(name, surname)
        val yearPublished: Long = 2004
        val author = Author(name, surname)
        val book = Book(isbn, title, author, yearPublished)
        val booksList = listOf(book)
        `when`(libraryServiceMock.getAllBooks()).thenReturn(booksList)

        val bookJsonList = library.getAllBooks()

        assertThat(bookJsonList).isNotEmpty
        val bookJson = bookJsonList.first()
        assertThat(bookJson).isNotNull
        assertThat(bookJson.isbn).isEqualTo(isbn)
        assertThat(bookJson.title).isEqualTo(title)
        assertThat(bookJson.author).isEqualTo(authorJson)
        assertThat(bookJson.yearPublished).isEqualTo(yearPublished)
    }

    @Test
    fun `Should retrieve books given an author's name`() {
        val authorName = "artemas"
        val author = Author("artemas", "smith")
        val book = Book(isbn, title, author, yearPublished)
        val booksList = listOf(book)
        `when`(libraryServiceMock.getBooksByAuthorName(authorName)).thenReturn(booksList)

        val bookJsonList = library.getBookByAuthorName(authorName)

        assertThat(bookJsonList).isNotEmpty
        val bookJson = bookJsonList.first()
        assertThat(bookJson).isNotNull
        assertThat(bookJson.isbn).isEqualTo(isbn)
        assertThat(bookJson.title).isEqualTo(title)
        assertThat(bookJson.author).isEqualTo(authorJson)
        assertThat(bookJson.yearPublished).isEqualTo(yearPublished)
    }

    @Test
    fun `Should retrieve books given an author`() {
        val author = Author("artemas", "smith")
        val books = listOf(Book(isbn, title, author, yearPublished))
        `when`(libraryServiceMock.getBooksByAuthor(author)).thenReturn(books)

        val booksByAuthor = library.getBookByAuthor(authorJson)

        assertThat(booksByAuthor).isNotEmpty
    }
}