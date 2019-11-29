package com.kotlin.example.booklibraryservice.mapper

import com.kotlin.example.booklibraryservice.dto.Book
import com.kotlin.example.booklibraryservice.json.BookJson

class BookMapper {
    companion object {
        fun bookJsonToDto(bookJson: BookJson): Book {
            return Book(bookJson.isbn, bookJson.title, AuthorMapper.authorJsonToDto(bookJson.author), bookJson.yearPublished)
        }
    }
}