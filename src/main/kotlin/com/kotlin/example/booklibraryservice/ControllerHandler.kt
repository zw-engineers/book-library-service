package com.kotlin.example.booklibraryservice

import com.kotlin.example.booklibraryservice.exception.AuthorNotValidException
import com.kotlin.example.booklibraryservice.exception.BookNotValidException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerHandler {

    @ExceptionHandler(BookNotValidException::class)
    fun handleBookNotValidException(): ResponseEntity<ApiError> {
        return ResponseEntity(ApiError("Book is not valid"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AuthorNotValidException::class)
    fun handleAuthorNotValidException(): ResponseEntity<ApiError> {
        return ResponseEntity(ApiError("Author is not valid"), HttpStatus.BAD_REQUEST)
    }
}