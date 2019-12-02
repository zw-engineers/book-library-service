package com.kotlin.example.booklibraryservice.mapper

import com.kotlin.example.booklibraryservice.exception.AuthorNotValidException
import com.kotlin.example.booklibraryservice.json.AuthorJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AuthorMapperTest {
    @Test
    fun `Should map AuthorJson to Author DTO`() {
        val name = "artemas"
        val surname = "muzanenhamo"
        val authorJson = AuthorJson(name, surname)

        val author = AuthorMapper.authorJsonToDto(authorJson)

        assertThat(author).isNotNull
        assertThat(author.name).isEqualTo(name)
        assertThat(author.surname).isEqualTo(surname)
    }

    @Test
    fun `Should throw an Exception when an author name is missing`() {
        val surname = "muzanenhamo"
        val authorJson = AuthorJson(null, surname)

        val exception = assertThrows<AuthorNotValidException> { AuthorMapper.authorJsonToDto(authorJson) }

        assertThat(exception.message).isEqualTo("Author name is missing. Please provide a name for the Author")
    }
}