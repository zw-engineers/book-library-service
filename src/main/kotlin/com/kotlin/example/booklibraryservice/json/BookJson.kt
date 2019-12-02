package com.kotlin.example.booklibraryservice.json

data class BookJson(
        val isbn: String?,
        val title: String?,
        val author: AuthorJson?,
        val yearPublished: Long?
)