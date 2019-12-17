package com.kotlin.example.booklibraryservice.web.integration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kotlin.example.booklibraryservice.exception.BookDoesNotExistsException
import com.kotlin.example.booklibraryservice.json.AuthorJson
import com.kotlin.example.booklibraryservice.json.BookJson
import com.kotlin.example.booklibraryservice.mapper.BookMapper
import com.kotlin.example.booklibraryservice.service.LibraryServiceImpl
import com.kotlin.example.booklibraryservice.web.LibraryEndpoint
import org.hamcrest.core.StringContains.containsString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.doAnswer
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
    @Autowired
    lateinit var mockMvc: MockMvc
    @MockBean
    lateinit var libraryServiceImpl: LibraryServiceImpl
    private val isbn = "123AAD"
    private val title = "Fly to the moon"
    private val authorJson = AuthorJson("Artemas", "Muzanenhamo")
    private val yearPublished: Long = 2004

    @Test
    fun `Should add a book to the library`() {
        val book = BookJson(isbn, title, authorJson, yearPublished)
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
        val book = BookJson(null, title, authorJson, yearPublished)
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
        val book = BookJson(isbn, null, authorJson, yearPublished)
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
        val author = AuthorJson(null, "Muzanenhamo")
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
        val author = AuthorJson(null, "Muzanenhamo")
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
        val author = AuthorJson("artemas", null)
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

    @Test
    fun `Should throw an exception when trying to edit a non existing book in the library`() {
        val author = AuthorJson("artemas", "muzanenhamo")
        val yearPublished: Long = 2008
        val bookJson = BookJson(isbn, title, author, yearPublished)
        val mapper = jacksonObjectMapper()
        val json = mapper.writeValueAsString(bookJson)
        val book = BookMapper.bookJsonToDto(bookJson)
        doAnswer { throw BookDoesNotExistsException("The book you are editing does not exist") }.`when`(libraryServiceImpl).editBook(book)

        mockMvc.perform(MockMvcRequestBuilders.post("/book/edit")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isBadRequest)
                .andExpect(content().string(containsString("The book you are editing does not exist")))
    }

    @Test
    fun `Should delete an existing book in the library`() {
        val author = AuthorJson("artemas", "muzanenhamo")
        val yearPublished: Long = 2008
        val book = BookJson(isbn, title, author, yearPublished)
        val mapper = jacksonObjectMapper()
        val json = mapper.writeValueAsString(book)

        mockMvc.perform(MockMvcRequestBuilders.delete("/book/remove")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk)
    }

    @Test
    fun `Should thrown an exception when trying to delete a non existing book in the library`() {
        val author = AuthorJson("artemas", "muzanenhamo")
        val yearPublished: Long = 2008
        val bookJson = BookJson(isbn, title, author, yearPublished)
        val mapper = jacksonObjectMapper()
        val json = mapper.writeValueAsString(bookJson)
        val book = BookMapper.bookJsonToDto(bookJson)
        doAnswer { throw BookDoesNotExistsException("The book you are deleting does not exist") }.`when`(libraryServiceImpl).deleteBook(book)

        mockMvc.perform(MockMvcRequestBuilders.delete("/book/remove")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isBadRequest)
                .andExpect(content().string(containsString("The book you are deleting does not exist")))
    }

    @Test
    fun `Should view a list of all book in the library`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk)
    }

    @Test
    fun `Should view a list of books from a given author name`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/book")
                .param("author-name", "artemas"))
                .andExpect(status().isOk)
    }

    @Test
    fun `Should view a book from a given author`() {
        val mapper = jacksonObjectMapper()
        val json = mapper.writeValueAsString(authorJson)

        mockMvc.perform(MockMvcRequestBuilders.post("/book/author")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk)
    }
}