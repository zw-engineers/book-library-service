package com.kotlin.example.booklibraryservice.mapper

import com.kotlin.example.booklibraryservice.dto.Author
import com.kotlin.example.booklibraryservice.dto.Book
import com.kotlin.example.booklibraryservice.json.AuthorJson
import com.kotlin.example.booklibraryservice.json.BookJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class BookMapperTest() {
    @Test
    @DisplayName("Should map BookJson to DTO")
    fun bookJsonToDTO() {
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
}