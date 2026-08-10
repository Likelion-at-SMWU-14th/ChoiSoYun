package com.likelion.seminarhw.controller;

import com.likelion.seminarhw.model.Book;
import com.likelion.seminarhw.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String getBooks(Model page) {
        var books = bookService.findAll();
        page.addAttribute("books", books);

        return "books";
    }

    @PostMapping("/books")
    public String addBook(Book book, Model page) {
        bookService.addBook(book);

        var books = bookService.findAll();
        page.addAttribute("books", books);

        return "books";
    }
}