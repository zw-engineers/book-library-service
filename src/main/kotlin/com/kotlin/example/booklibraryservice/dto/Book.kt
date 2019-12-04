package com.kotlin.example.booklibraryservice.dto

import lombok.Data
import lombok.EqualsAndHashCode
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "books")
@Data
@EqualsAndHashCode
class Book(
        @MongoId
        val isbn: String,
        val title: String,
        val author: Author,
        val yearPublished: Long
)