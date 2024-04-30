package com.books.bookservice.service.impl;


import com.books.bookservice.dto.BookRequest;
import com.books.bookservice.dto.BookResponse;
import com.books.bookservice.model.Book;
import com.books.bookservice.reposiotry.BookRepository;
import com.books.bookservice.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public void addBook(BookRequest bookRequest) {
        Book book = Book.builder()
                .name(bookRequest.getName())
                .description(bookRequest.getDescription())
                .price(bookRequest.getPrice())
                .build();
        repository.save(book);
        log.info("Book saved: {}", book);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        List<Book> books = repository.findAll();
        List<BookResponse> res = books.stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .collect(Collectors.toList());
        return res;
    }
}
