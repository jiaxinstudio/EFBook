package com.book.ef.controller;

import com.book.ef.entity.Book;
import com.book.ef.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }



    @GetMapping("list")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @PostMapping
    public void save(@RequestBody Book book) {
        bookService.save(book);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable int id, @RequestBody Book book) {
        return book;
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping
    public Book getBookByISBN(@RequestParam("isbn") String isbn) {
        return bookService.getBookByIsbn(isbn);
    }
}
