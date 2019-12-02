package com.kotlin.example.booklibraryservice.mapper

import com.kotlin.example.booklibraryservice.dto.Book
import com.kotlin.example.booklibraryservice.exception.BookNotValidException
import com.kotlin.example.booklibraryservice.json.BookJson

class BookMapper {
    companion object {
        fun bookJsonToDto(bookJson: BookJson?): Book {
            bookJson ?: throw BookNotValidException("Book is not Valid")
            val isbn = bookJson.isbn
                    ?: throw BookNotValidException("Book ISBN is missing. Please provide an ISBN for your book")
            val title = bookJson.title
                    ?: throw BookNotValidException("Book title is missing. Please provide a title for your book")
            val yearPublished = bookJson.yearPublished
                    ?: throw BookNotValidException("Book published year is missing")

            return Book(isbn, title, AuthorMapper.authorJsonToDto(bookJson.author), yearPublished)
        }
    }
}