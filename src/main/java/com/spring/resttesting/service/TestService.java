package com.spring.resttesting.service;

import com.spring.resttesting.model.Book;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public String returnTestString() {

        return "test";
    }

    public Book returnTestBook() {

        return new Book("testAuthor","testTitle");

    }


}
