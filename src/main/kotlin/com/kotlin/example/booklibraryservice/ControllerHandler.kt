package com.kotlin.example.booklibraryservice

import com.kotlin.example.booklibraryservice.exception.AuthorNotValidException
import com.kotlin.example.booklibraryservice.exception.BookDoesNotExistsException
import com.kotlin.example.booklibraryservice.exception.BookNotValidException
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerHandler : Logging {

    @ExceptionHandler(BookNotValidException::class)
    fun handleBookNotValidException(exception: BookNotValidException): ResponseEntity<ApiError> {
        val message = exception.localizedMessage
        logger.info(message)
        return ResponseEntity(ApiError(message), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AuthorNotValidException::class)
    fun handleAuthorNotValidException(exception: AuthorNotValidException): ResponseEntity<ApiError> {
        val message = exception.localizedMessage
        logger.info(message)
        return ResponseEntity(ApiError(message), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(BookDoesNotExistsException::class)
    fun handleBookDoesNotExistsException(exception: BookDoesNotExistsException): ResponseEntity<ApiError> {
        val message = exception.localizedMessage
        logger.info(message)
        return ResponseEntity(ApiError(message), HttpStatus.BAD_REQUEST)
    }
}