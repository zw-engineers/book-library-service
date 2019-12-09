package com.kotlin.example.booklibraryservice.mapper

import com.kotlin.example.booklibraryservice.dto.Author
import com.kotlin.example.booklibraryservice.dto.Book
import com.kotlin.example.booklibraryservice.exception.AuthorNotValidException
import com.kotlin.example.booklibraryservice.exception.BookNotValidException
import com.kotlin.example.booklibraryservice.json.AuthorJson
import com.kotlin.example.booklibraryservice.json.BookJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class BookMapperTest {
    @Test
    fun `Should map BookJson to DTO`() {
        val isbn = "123ABC"
        val title = "fly to the moon"
        val authorJson = AuthorJson("artemas", "smith")
        val yearPublished: Long = 2004
        val bookJson = BookJson(isbn, title, authorJson, yearPublished)
        val author = Author("artemas", "smith")

        val book: Book = BookMapper.bookJsonToDto(bookJson)

        assertThat(book).isNotNull
        assertThat(book.isbn).isEqualTo(isbn)
        assertThat(book.title).isEqualTo(title)
        assertThat(book.author).isEqualTo(author)
        assertThat(book.yearPublished).isEqualTo(yearPublished)
    }

    @Test
    fun `Should throw an exception when Book does not have an isbn value`() {
        val title = "fly to the moon"
        val authorJson = AuthorJson("artemas", "smith")
        val yearPublished: Long = 2004
        val bookJson = BookJson(null, title, authorJson, yearPublished)

        val exception = assertThrows<BookNotValidException> { BookMapper.bookJsonToDto(bookJson) }

        assertThat(exception.message).isEqualTo("Book ISBN is missing. Please provide an ISBN for your book")
    }

    @Test
    fun `Should throw an exception when Book does not have a title`() {
        val isbn = "123ABC"
        val authorJson = AuthorJson("artemas", "smith")
        val yearPublished: Long = 2004
        val bookJson = BookJson(isbn, null, authorJson, yearPublished)

        val exception = assertThrows<BookNotValidException> { BookMapper.bookJsonToDto(bookJson) }

        assertThat(exception.message).isEqualTo("Book title is missing. Please provide a title for your book")
    }

    @Test
    fun `Should throw an exception when Book does not have an author value`() {
        val isbn = "123ABC"
        val title = "fly to the moon"
        val yearPublished: Long = 2004
        val bookJson = BookJson(isbn, title, null, yearPublished)

        val exception = assertThrows<AuthorNotValidException> { BookMapper.bookJsonToDto(bookJson) }

        assertThat(exception.message).isEqualTo("Author is invalid. Please provide a valid Author")
    }

    @Test
    fun `Should throw an exception when Book does not have an author name`() {
        val isbn = "123ABC"
        val title = "fly to the moon"
        val yearPublished: Long = 2004
        val authorJson = AuthorJson(null, "smith")
        val bookJson = BookJson(isbn, title, authorJson, yearPublished)

        val exception = assertThrows<AuthorNotValidException> { BookMapper.bookJsonToDto(bookJson) }

        assertThat(exception.message).isEqualTo("Author name is missing. Please provide a name for the Author")
    }

    @Test
    fun `Should throw an exception when Book does not have an author surname`() {
        val isbn = "123ABC"
        val title = "fly to the moon"
        val yearPublished: Long = 2004
        val authorJson = AuthorJson("artemas", null)
        val bookJson = BookJson(isbn, title, authorJson, yearPublished)

        val exception = assertThrows<AuthorNotValidException> { BookMapper.bookJsonToDto(bookJson) }

        assertThat(exception.message).isEqualTo("Author surname is missing. Please provide a surname for the Author")
    }

    @Test
    fun `Should throw an exception when the Book does not have a year published value`() {
        val isbn = "123ABC"
        val title = "fly to the moon"
        val authorJson = AuthorJson("artemas", null)
        val bookJson = BookJson(isbn, title, authorJson, null)

        val exception = assertThrows<BookNotValidException> { BookMapper.bookJsonToDto(bookJson) }

        assertThat(exception.message).isEqualTo("Book published year is missing")
    }

    @Test
    fun `Should throw an exception when Book is not valid`() {
        val exception = assertThrows<BookNotValidException> { BookMapper.bookJsonToDto(null) }

        assertThat(exception.message).isEqualTo("Book is not Valid")
    }

    @Test
    fun `Should map a BookDTO to a BookJson`() {
        val isbn = "123ABC"
        val title = "fly to the moon"
        val name = "artemas"
        val surname = "muzanenhamo"
        val author = Author(name, surname)
        val yearPublished: Long = 2008
        val book = Book(isbn, title, author, yearPublished)
        val authorJson = AuthorJson(name, surname)

        val bookJson = BookMapper.bookDtoToJson(book)

        assertThat(bookJson).isNotNull
        assertThat(bookJson.isbn).isEqualTo(isbn)
        assertThat(bookJson.title).isEqualTo(title)
        assertThat(bookJson.author).isEqualTo(authorJson)
        assertThat(bookJson.yearPublished).isEqualTo(yearPublished)
    }

    @Test
    fun `Should map a list of books to a list of booksJson`() {
        val isbn = "123ABC"
        val title = "fly to the moon"
        val name = "artemas"
        val surname = "muzanenhamo"
        val author = Author(name, surname)
        val yearPublished: Long = 2008
        val book = Book(isbn, title, author, yearPublished)
        val booksDTO = listOf(book)
        val authorJson = AuthorJson(name, surname)

        val booksJson = BookMapper.booksDtoToJson(booksDTO)

        assertThat(booksJson).isNotEmpty
        assertThat(booksJson).hasSize(1)
        val bookJson = booksJson.first()
        assertThat(bookJson).isNotNull
        assertThat(bookJson.isbn).isEqualTo(isbn)
        assertThat(bookJson.title).isEqualTo(title)
        assertThat(bookJson.author).isEqualTo(authorJson)
        assertThat(bookJson.yearPublished).isEqualTo(yearPublished)
    }
}