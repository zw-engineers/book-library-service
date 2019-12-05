package com.kotlin.example.booklibraryservice.controller.integration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kotlin.example.booklibraryservice.controller.LibraryEndpoint
import com.kotlin.example.booklibraryservice.json.AuthorJson
import com.kotlin.example.booklibraryservice.json.BookJson
import com.kotlin.example.booklibraryservice.service.LibraryServiceImpl
import org.hamcrest.core.StringContains.containsString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(LibraryEndpoint::class)
class LibraryEndpointIntegrationTest {
    @Autowired lateinit var mockMvc: MockMvc
    @MockBean lateinit var libraryServiceImpl: LibraryServiceImpl

    @Test
    fun `Should add a book to the library`() {
        val isbn = "123AAD"
        val title = "Fly to the moon"
        val author = AuthorJson("Artemas", "Muzanenhamo")
        val yearPublished: Long = 2004
        val book = BookJson(isbn, title, author, yearPublished)
        val mapper = jacksonObjectMapper()
        val json = mapper.writeValueAsString(book)

        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk)
    }

    @Test
    fun `Should throw BookNotValidException when ISBN is null`() {
        val title = "Fly to the moon"
        val author = AuthorJson("Artemas", "Muzanenhamo")
        val yearPublished: Long = 2004
        val book = BookJson(null, title, author, yearPublished)
        val mapper = jacksonObjectMapper()
        val json = mapper.writeValueAsString(book)

        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isBadRequest)
                .andExpect(content().string(containsString("Book ISBN is missing. Please provide an ISBN for your book")))
    }

    @Test
    fun `Should throw BookNotValidException when title is null`() {
        val isbn = "123AAD"
        val author = AuthorJson("Artemas", "Muzanenhamo")
        val yearPublished: Long = 2004
        val book = BookJson(isbn, null, author, yearPublished)
        val mapper = jacksonObjectMapper()
        val json = mapper.writeValueAsString(book)

        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isBadRequest)
                .andExpect(content().string(containsString("Book title is missing. Please provide a title for your book")))
    }

    @Test
    fun `Should throw AuthorNotValidException when author is null`() {
        val isbn = "123AAD"
        val title = "Fly to the moon"
        val author = AuthorJson(null, "Muzanenhamo")
        val yearPublished: Long = 2004
        val book = BookJson(isbn, title, author, yearPublished)
        val mapper = jacksonObjectMapper()
        val json = mapper.writeValueAsString(book)

        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isBadRequest)
                .andExpect(content().string(containsString("Author name is missing. Please provide a name for the Author")))
    }

    @Test
    fun `Should throw AuthorNotValidException when Author name is null`() {
        val isbn = "123AAD"
        val title = "Fly to the moon"
        val author = AuthorJson(null, "Muzanenhamo")
        val yearPublished: Long = 2004
        val book = BookJson(isbn, title, author, yearPublished)
        val mapper = jacksonObjectMapper()
        val json = mapper.writeValueAsString(book)

        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isBadRequest)
                .andExpect(content().string(containsString("Author name is missing. Please provide a name for the Author")))
    }

    @Test
    fun `Should throw AuthorNotValidException when Author surname is null`() {
        val isbn = "123AAD"
        val title = "Fly to the moon"
        val author = AuthorJson("artemas", null)
        val yearPublished: Long = 2004
        val book = BookJson(isbn, title, author, yearPublished)
        val mapper = jacksonObjectMapper()
        val json = mapper.writeValueAsString(book)

        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isBadRequest)
                .andExpect(content().string(containsString("Author surname is missing. Please provide a surname for the Author")))
    }

    @Test
    fun `Should throw BookNotValidException when YearPublished is null`() {
        val isbn = "123AAD"
        val title = "Fly to the moon"
        val author = AuthorJson("artemas", "muzanenhamo")
        val book = BookJson(isbn, title, author, null)
        val mapper = jacksonObjectMapper()
        val json = mapper.writeValueAsString(book)

        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isBadRequest)
                .andExpect(content().string(containsString("Book published year is missing")))
    }

    @Test
    fun `Should edit an existing book in the library`() {
        val isbn = "123AAD"
        val title = "Fly to the moon"
        val author = AuthorJson("artemas", "muzanenhamo")
        val yearPublished: Long = 2008
        val book = BookJson(isbn, title, author, yearPublished)
        val mapper = jacksonObjectMapper()
        val json = mapper.writeValueAsString(book)

        mockMvc.perform(MockMvcRequestBuilders.post("/book/edit")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk)
    }
}