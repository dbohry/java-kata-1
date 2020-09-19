package org.echocat.kata.java.part1.magazine.service;

import lombok.AllArgsConstructor;
import org.echocat.kata.java.part1.magazine.entity.Magazine;
import org.echocat.kata.java.part1.magazine.repository.MagazineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MagazineService {

    private final MagazineRepository repository;

    public List<Magazine> findAll() {
        return repository.findAll();
    }

    public List<Magazine> findByAuthor(String author) {
        return repository.findByAuthor(author);
    }

    public Optional<Magazine> findByIsbn(String isbn) {
        return repository.findByIsbn(isbn);
    }

}
