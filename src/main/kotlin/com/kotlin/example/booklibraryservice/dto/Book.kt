package com.kotlin.example.booklibraryservice.dto

class Book(
        val isbn: String,
        val title: String,
        val author: Author,
        val yearPublished: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (isbn != other.isbn) return false
        if (title != other.title) return false
        if (author != other.author) return false
        if (yearPublished != other.yearPublished) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isbn.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + yearPublished.hashCode()
        return result
    }
}