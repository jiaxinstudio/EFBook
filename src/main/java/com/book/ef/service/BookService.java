package com.book.ef.service;

import com.book.ef.entity.Book;
import com.book.ef.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getBooks() {
        return repository.findAll();
    }

    public Book getBookById(Long id) {
        return repository.findById(id).get();
    }

    public Book getBookByIsbn(String isbn) {
        return repository.findByIsbn(isbn).get();
    }

    public void save(Book book) {
        repository.save(book);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
