package com.spring.resttesting.controller;

import com.spring.resttesting.model.Book;
import com.spring.resttesting.service.TestService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class HomeControllerTest {

    private static final String EXPECTED_RESPONSE = "hi";

    private MockMvc mockMvc;

    @Mock
    private TestService testService;

    @InjectMocks
    private HomeController homeController;


    @Before
    public void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void shouldReturnTheExpectedStringWhenHittingTheEndpoint() throws Exception {

        when(testService.returnTestString()).thenReturn(EXPECTED_RESPONSE);

        mockMvc.perform(get("/testing"))
                .andExpect(status().isOk())
                .andExpect(content().string(EXPECTED_RESPONSE));

        verify(testService).returnTestString();

    }


    @Test
    public void shouldReturnBookObject() throws  Exception{

        when(testService.returnTestBook()).thenReturn(new Book("testAuthor","testTitle"));

        mockMvc.perform(get("/testing/json")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author", Matchers.is("testAuthor")))
                .andExpect(jsonPath("$.title",Matchers.is("testTitle")))
                .andExpect(jsonPath("$.*",Matchers.hasSize(2)));

        verify(testService).returnTestBook();

    }


    @Test
    public void shouldSuccessfullyAddObject() throws Exception{

        String json = "{\n" +
                "  \"author\": \"testAuthor\",\n" +
                "  \"title\": \"testTitle\"\n" +
                "}";

        mockMvc.perform(post("/testing/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author", Matchers.is("testAuthor")))
                .andExpect(jsonPath("$.title", Matchers.is("testTitle")))
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)));

    }
}