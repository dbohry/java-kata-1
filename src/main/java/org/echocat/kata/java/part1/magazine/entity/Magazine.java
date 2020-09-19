package org.echocat.kata.java.part1.magazine.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.echocat.kata.java.part1.author.entity.Author;

import java.util.List;

@Data
@Accessors(chain = true)
public class Magazine {

    String title;
    String isbn;
    List<Author> authors;
    String publishedAt;

}
