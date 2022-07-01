package com.knoldus.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knoldus.entity.Book;
import com.knoldus.repository.BookRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * the type BookController
 */
@ContextConfiguration(classes = {BookController.class})
@ExtendWith(SpringExtension.class)
class BookControllerTest {
    @Autowired
    private BookController bookController;

    @MockBean
    private BookRepository bookRepository;

    /**
     * create Book Record
     * @throws Exception
     */
    @Test
    void testCreateBookRecord() throws Exception {
        Book book = new Book();
        book.setBookId(123L);
        book.setName("Name");
        book.setRating(1);
        book.setSummary("Summary");
        when(this.bookRepository.save((Book) any())).thenReturn(book);

        Book book1 = new Book();
        book1.setBookId(123L);
        book1.setName("Name");
        book1.setRating(1);
        book1.setSummary("Summary");
        String content = (new ObjectMapper()).writeValueAsString(book1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/book/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"bookId\":123,\"name\":\"Name\",\"summary\":\"Summary\",\"rating\":1}"));
    }

    /**
     * get aal boolRecord test
     * @throws Exception
     */
    @Test
    void testGetAllBookRecords() throws Exception {
        when(this.bookRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/get");
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     *
     * @throws Exception
     */
    @Test
    void testGetAllBookRecords2() throws Exception {
        when(this.bookRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/book/get");
        getResult.contentType(MediaType.APPLICATION_JSON);
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * get book by Id test
     * @throws Exception
     */
    @Test
    void testGetBookById() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/*");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
    }

    /**
     * update BookRecord test
     * @throws Exception
     */
    @Test
    void testUpdateBookRecord() throws Exception {
        Book book = new Book();
        book.setBookId(123L);
        book.setName("Name");
        book.setRating(1);
        book.setSummary("Summary");
        Optional<Book> ofResult = Optional.of(book);

        Book book1 = new Book();
        book1.setBookId(123L);
        book1.setName("Name");
        book1.setRating(1);
        book1.setSummary("Summary");
        when(this.bookRepository.save((Book) any())).thenReturn(book1);
        when(this.bookRepository.findById((Long) any())).thenReturn(ofResult);

        Book book2 = new Book();
        book2.setBookId(123L);
        book2.setName("Name");
        book2.setRating(1);
        book2.setSummary("Summary");
        String content = (new ObjectMapper()).writeValueAsString(book2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/book/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"bookId\":123,\"name\":\"Name\",\"summary\":\"Summary\",\"rating\":1}"));
    }
}

