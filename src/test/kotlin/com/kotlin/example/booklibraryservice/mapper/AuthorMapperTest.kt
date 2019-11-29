package com.kotlin.example.booklibraryservice.mapper

import com.kotlin.example.booklibraryservice.json.AuthorJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AuthorMapperTest {
    @Test
    @DisplayName("Should map AuthorJson to Author DTO")
    fun authorJsonToDTO() {
        val name = "artemas"
        val surname = "muzanenhamo"
        val authorJson = AuthorJson(name, surname)

        val author = AuthorMapper.authorJsonToDto(authorJson)

        assertThat(author).isNotNull
        assertThat(author.name).isEqualTo(name)
        assertThat(author.surname).isEqualTo(surname)
    }
}