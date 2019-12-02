package com.kotlin.example.booklibraryservice

import com.kotlin.example.booklibraryservice.exception.AuthorNotValidException
import com.kotlin.example.booklibraryservice.exception.BookNotValidException
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerHandler : Logging {

    @ExceptionHandler(BookNotValidException::class)
    fun handleBookNotValidException(ex: BookNotValidException): ResponseEntity<ApiError> {
        logger.info(ex.message!!)
        return ResponseEntity(ApiError("Book is not valid"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AuthorNotValidException::class)
    fun handleAuthorNotValidException(ex: AuthorNotValidException): ResponseEntity<ApiError> {
        logger.info(ex.message!!)
        return ResponseEntity(ApiError("Author is not valid"), HttpStatus.BAD_REQUEST)
    }
}