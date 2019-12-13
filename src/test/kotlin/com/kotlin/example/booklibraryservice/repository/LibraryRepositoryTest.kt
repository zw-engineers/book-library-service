package com.kotlin.example.booklibraryservice.repository

import com.kotlin.example.booklibraryservice.dto.Author
import com.kotlin.example.booklibraryservice.dto.Book
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
class LibraryRepositoryTest(@Autowired private val libraryRepository: LibraryRepository) {

    private val book1 = Book("225ABC", "fly to the moon", Author("artemas", "muzanenhamo"), 2004)
    private val book2 = Book("333GEF", "take me away", Author("thomas", "jenkins"), 2008)
    private val book3 = Book("123ABC", "love within", Author("mark", "martins"), 2009)
    private val book4 = Book("554OPP", "who knew?", Author("steve", "roberts"), 2012)
    private val book5 = Book("993NVM", "how to get rich, quick!", Author("artemas", "muzanenhamo"), 2006)

    @BeforeEach
    fun setUp() {
        val books = listOf(book1, book2, book3, book4, book5)
        libraryRepository.deleteAll()
        libraryRepository.insert(books)
    }

    @Test
    fun `Should return books given the author name`() {
        val booksByAuthorName = libraryRepository.findAllByAuthorName("artemas")

        assertThat(booksByAuthorName).isNotEmpty
        assertThat(booksByAuthorName).hasSize(2)
        assertThat(booksByAuthorName).containsExactlyInAnyOrder(book1, book5)
    }

    @Test
    fun `Should return no books if author name does not exist in the library`() {
        val booksByAuthorName = libraryRepository.findAllByAuthorName("stacey")

        assertThat(booksByAuthorName).isEmpty()
    }
}