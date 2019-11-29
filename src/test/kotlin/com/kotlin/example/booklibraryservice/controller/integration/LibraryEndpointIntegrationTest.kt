package com.kotlin.example.booklibraryservice.controller.integration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kotlin.example.booklibraryservice.controller.LibraryEndpoint
import com.kotlin.example.booklibraryservice.json.AuthorJson
import com.kotlin.example.booklibraryservice.json.BookJson
import com.kotlin.example.booklibraryservice.service.LibraryServiceImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
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

    // TODO: Add case when BookNotValidException is thrown
}