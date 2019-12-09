package com.kotlin.example.booklibraryservice.mapper

import com.kotlin.example.booklibraryservice.dto.Author
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
    fun `Should throw an Exception when an author passed is not valid or null`() {
        val exception = assertThrows<AuthorNotValidException> { AuthorMapper.authorJsonToDto(null) }

        assertThat(exception.message).isEqualTo("Author is invalid. Please provide a valid Author")
    }

    @Test
    fun `Should throw an Exception when an author name is missing`() {
        val surname = "muzanenhamo"
        val authorJson = AuthorJson(null, surname)

        val exception = assertThrows<AuthorNotValidException> { AuthorMapper.authorJsonToDto(authorJson) }

        assertThat(exception.message).isEqualTo("Author name is missing. Please provide a name for the Author")
    }

    @Test
    fun `Should throw an Exception when an author surname is missing`() {
        val name = "artemas"
        val authorJson = AuthorJson(name, null)

        val exception = assertThrows<AuthorNotValidException> { AuthorMapper.authorJsonToDto(authorJson) }

        assertThat(exception.message).isEqualTo("Author surname is missing. Please provide a surname for the Author")
    }

    @Test
    fun `Should map AuthorDTO to AuthorJson`() {
        val name = "artemas"
        val surname = "muzanenhamo"
        val author = Author(name, surname)

        val authorJson = AuthorMapper.authorDtoToJson(author)

        assertThat(authorJson).isNotNull
        assertThat(authorJson.name).isEqualTo(name)
        assertThat(authorJson.surname).isEqualTo(surname)
    }
}