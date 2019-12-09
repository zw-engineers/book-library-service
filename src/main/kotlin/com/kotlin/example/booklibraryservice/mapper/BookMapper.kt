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
            val author = AuthorMapper.authorJsonToDto(bookJson.author)

            return Book(isbn, title, author, yearPublished)
        }

        fun bookDtoToJson(book: Book): BookJson {
            return BookJson(book.isbn, book.title, AuthorMapper.authorDtoToJson(book.author), book.yearPublished)
        }

        fun booksDtoToJson(books: List<Book>): List<BookJson> {
            return books
                    .map { book -> bookDtoToJson(book) }
                    .toList()
        }
    }
}