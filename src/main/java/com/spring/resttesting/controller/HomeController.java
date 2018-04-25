package com.spring.resttesting.controller;

import com.spring.resttesting.model.Book;
import com.spring.resttesting.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testing")
public class HomeController {

    private TestService testService;

    @Autowired
    public HomeController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public String stringTest() {

        return testService.returnTestString();
    }

    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book bookObjectTest() {

      return testService.returnTestBook();

    }

    @PostMapping(value = "/post", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Book postMethodTest(@RequestBody  Book book) {

        return book;

    }

}
