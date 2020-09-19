package org.echocat.kata.java.part1.book.service;

import lombok.AllArgsConstructor;
import org.echocat.kata.java.part1.book.entity.Book;
import org.echocat.kata.java.part1.book.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository repository;

    public List<Book> findAll() {
        return repository.findAll();
    }

    public List<Book> findByAuthor(String author) {
        return repository.findByAuthor(author);
    }

    public Optional<Book> findByIsbn(String isbn) {
        return repository.findByIsbn(isbn);
    }

}
