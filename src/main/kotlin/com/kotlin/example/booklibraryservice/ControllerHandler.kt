package com.kotlin.example.booklibraryservice

import com.kotlin.example.booklibraryservice.exception.BookNotValidException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerHandler {

    @ExceptionHandler(BookNotValidException::class)
    fun handleBookNotValidException(): ResponseEntity<ApiError> {
        return ResponseEntity(ApiError("Reservation Secret is not set"), HttpStatus.BAD_REQUEST)
    }
}