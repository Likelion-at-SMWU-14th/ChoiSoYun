package com.likelion.seminarhw.controller;

import com.likelion.seminarhw.dto.BookDto;
import com.likelion.seminarhw.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDto> getBooks() {
        return bookService.findAllDto();
    }

    @PostMapping
    public BookDto addBook(@RequestBody BookDto bookDto) {
        return bookService.addBookDto(bookDto);
    }
}