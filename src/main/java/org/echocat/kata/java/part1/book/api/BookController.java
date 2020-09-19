package org.echocat.kata.java.part1.book.api;

import lombok.AllArgsConstructor;
import org.echocat.kata.java.part1.book.entity.Book;
import org.echocat.kata.java.part1.book.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("books")
@AllArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping
    public ResponseEntity<List<Book>> find(@RequestParam(value = "isbn", required = false) String isbn,
                                           @RequestParam(value = "author", required = false) String author) {
        List<Book> response = new ArrayList<>();

        if (isbn != null) {
            Optional<Book> result = service.findByIsbn(isbn);
            result.ifPresent(response::add);
        } else if (author != null) {
            response = service.findByAuthor(author);
        } else {
            response = service.findAll();
        }

        return ResponseEntity.ok(response);
    }

}
