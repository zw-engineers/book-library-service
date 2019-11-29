package com.kotlin.example.booklibraryservice.mapper

import com.kotlin.example.booklibraryservice.dto.Author
import com.kotlin.example.booklibraryservice.json.AuthorJson

class AuthorMapper {
    companion object {
        fun authorJsonToDto(authorJson: AuthorJson): Author {
            return Author(authorJson.name, authorJson.surname)
        }
    }
}