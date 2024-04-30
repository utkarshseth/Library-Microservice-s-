package com.books.bookservice.service;

import com.books.bookservice.dto.BookRequest;
import com.books.bookservice.dto.BookResponse;

import java.util.List;


public interface BookService {

    public void addBook(BookRequest bookRequest);

    List<BookResponse> getAllBooks();
}
