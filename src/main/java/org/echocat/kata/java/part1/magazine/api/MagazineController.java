package org.echocat.kata.java.part1.magazine.api;

import lombok.AllArgsConstructor;
import org.echocat.kata.java.part1.magazine.entity.Magazine;
import org.echocat.kata.java.part1.magazine.service.MagazineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("magazines")
@AllArgsConstructor
public class MagazineController {

    private final MagazineService service;

    @GetMapping
    public ResponseEntity<List<Magazine>> find(@RequestParam(value = "isbn", required = false) String isbn,
                                               @RequestParam(value = "author", required = false) String author) {
        List<Magazine> response = new ArrayList<>();

        if (isbn != null) {
            Optional<Magazine> result = service.findByIsbn(isbn);
            result.ifPresent(response::add);
        } else if (author != null) {
            response = service.findByAuthor(author);
        } else {
            response = service.findAll();
        }

        return ResponseEntity.ok(response);
    }

}
