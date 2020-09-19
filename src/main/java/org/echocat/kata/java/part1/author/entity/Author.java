package org.echocat.kata.java.part1.author.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Author {

    private String email;
    private String firstName;
    private String lastName;

}
