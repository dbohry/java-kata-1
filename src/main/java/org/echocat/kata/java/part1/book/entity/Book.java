package org.echocat.kata.java.part1.book.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.echocat.kata.java.part1.author.entity.Author;

import java.util.List;

@Data
@Accessors(chain = true)
public class Book {

    private String title;
    private String isbn;
    private List<Author> authors;
    private String description;

}
