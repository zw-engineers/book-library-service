package com.kotlin.example.booklibraryservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.kotlin.example.booklibraryservice.json.AuthorJson
import com.kotlin.example.booklibraryservice.json.BookJson
import net.minidev.json.JSONObject
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class LibraryEndpointIntegrationTest(
        @Autowired
        private val mockMvc: MockMvc) {

    @Test
    @DisplayName("Should add a book to the library")
    fun addBook() {
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
}