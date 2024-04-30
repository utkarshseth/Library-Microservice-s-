package com.books.bookservice.controller;

import com.books.bookservice.dto.BookRequest;
import com.books.bookservice.dto.BookResponse;
import com.books.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BooksController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void creteBook(@RequestBody BookRequest bookRequest) {
        bookService.addBook(bookRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getAllBooks() {
        List<BookResponse> res = bookService.getAllBooks();
        return res;
    }
}
