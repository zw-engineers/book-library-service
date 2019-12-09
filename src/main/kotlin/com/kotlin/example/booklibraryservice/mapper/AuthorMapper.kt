package com.kotlin.example.booklibraryservice.mapper

import com.kotlin.example.booklibraryservice.dto.Author
import com.kotlin.example.booklibraryservice.exception.AuthorNotValidException
import com.kotlin.example.booklibraryservice.json.AuthorJson

class AuthorMapper {
    companion object {
        fun authorJsonToDto(authorJson: AuthorJson?): Author {
            authorJson?: throw AuthorNotValidException("Author is invalid. Please provide a valid Author")
            val name = authorJson.name
                    ?: throw AuthorNotValidException("Author name is missing. Please provide a name for the Author")
            val surname = authorJson.surname
                    ?: throw AuthorNotValidException("Author surname is missing. Please provide a surname for the Author")

            return Author(name, surname)
        }

        fun authorDtoToJson(author: Author): AuthorJson {
            return AuthorJson(author.name, author.surname)
        }
    }
}