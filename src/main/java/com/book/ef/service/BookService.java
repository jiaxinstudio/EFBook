package com.book.ef.service;

import com.book.ef.dto.BookDto;
import com.book.ef.entity.Book;
import com.book.ef.exception.BookNotFoundException;
import com.book.ef.repository.BookRepository;
import com.book.ef.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository repository;
    private final InventoryRepository inventoryRepository;

    public BookService(BookRepository repository, InventoryRepository inventoryRepository) {
        this.repository = repository;
        this.inventoryRepository = inventoryRepository;
    }

    public List<Book> getBooks() {
        return repository.findAll();
    }

    public Book getBookById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BookNotFoundException(String.format("Book with id %s not found", id)));
    }

    public Book getBookByIsbn(String isbn) {
        return repository.findByIsbn(isbn).get();
    }

    public Book save(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setCoverUrl(bookDto.getCoverUrl());
        book.setIsbn(bookDto.getIsbn());
        book.setPrice(bookDto.getPrice());
        return repository.save(book);
    }

    @Transactional
    public void delete(Long id) {
        inventoryRepository.deleteByBook_Id(id);
        repository.deleteById(id);
    }

    public Book updateBook(Long id, BookDto bookDto) {
        Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(String.format("Book with id %s not found", id)));
        Optional.ofNullable(bookDto.getTitle()).ifPresent(book::setTitle);
        Optional.ofNullable(bookDto.getAuthor()).ifPresent(book::setAuthor);
        Optional.ofNullable(bookDto.getIsbn()).ifPresent(book::setIsbn);
        Optional.ofNullable(bookDto.getCoverUrl()).ifPresent(book::setCoverUrl);
        Optional.ofNullable(bookDto.getPrice()).ifPresent(book::setPrice);

        return repository.save(book);
    }
}
