package com.kotlin.example.booklibraryservice.mapper

import com.kotlin.example.booklibraryservice.dto.Author
import com.kotlin.example.booklibraryservice.exception.AuthorNotValidException
import com.kotlin.example.booklibraryservice.json.AuthorJson

class AuthorMapper {
    companion object {
        fun authorJsonToDto(authorJson: AuthorJson): Author {
            authorJson.name?: throw AuthorNotValidException("Author name is missing. Please provide a name for the Author")
            authorJson.surname?: throw AuthorNotValidException("Author surname is missing. Please provide a surname for the Author")
            return Author(authorJson.name, authorJson.surname)
        }
    }
}