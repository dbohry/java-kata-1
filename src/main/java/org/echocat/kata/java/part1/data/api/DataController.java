package org.echocat.kata.java.part1.data.api;

import lombok.AllArgsConstructor;
import org.echocat.kata.java.part1.book.entity.Book;
import org.echocat.kata.java.part1.book.service.BookService;
import org.echocat.kata.java.part1.magazine.entity.Magazine;
import org.echocat.kata.java.part1.magazine.service.MagazineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("data")
@AllArgsConstructor
public class DataController {

    private final BookService bookService;
    private final MagazineService magazineService;

    @GetMapping
    public ResponseEntity<List<Object>> getAll() {
        List<Book> books = bookService.findAll();
        List<Magazine> magazines = magazineService.findAll();

        List<Object> result = Stream.concat(books.stream(), magazines.stream())
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

}
