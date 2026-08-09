package com.likelion.seminarhw.service;

import com.likelion.seminarhw.dto.BookDto;
import com.likelion.seminarhw.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    // MVC용
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> findAll() {
        return books;
    }


    // REST용
    private List<BookDto> bookDtos = new ArrayList<>();

    public BookDto addBookDto(BookDto bookDto) {
        bookDtos.add(bookDto);
        return bookDto;
    }

    public List<BookDto> findAllDto() {
        return bookDtos;
    }
}