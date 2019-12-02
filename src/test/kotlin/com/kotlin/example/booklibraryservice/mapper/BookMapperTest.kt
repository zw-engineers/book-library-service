package com.kotlin.example.booklibraryservice.mapper

import com.kotlin.example.booklibraryservice.dto.Author
import com.kotlin.example.booklibraryservice.dto.Book
import com.kotlin.example.booklibraryservice.exception.BookNotValidException
import com.kotlin.example.booklibraryservice.json.AuthorJson
import com.kotlin.example.booklibraryservice.json.BookJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class BookMapperTest() {
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
    fun `Should throw an exception when Book does not have a title`() {
        val isbn = "123ABC"
        val authorJson = AuthorJson("artemas", "smith")
        val yearPublished: Long = 2004
        val bookJson = BookJson(isbn, null, authorJson, yearPublished)

        val exception = assertThrows<BookNotValidException> { BookMapper.bookJsonToDto(bookJson) }

        assertThat(exception.message).isEqualTo("Book title is missing. Please provide a title for your book")
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
    fun `Should throw an exception when Book is not valid`() {
        val exception = assertThrows<BookNotValidException> { BookMapper.bookJsonToDto(null) }

        assertThat(exception.message).isEqualTo("Book is not Valid")
    }
}