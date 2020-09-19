package org.echocat.kata.java.part1.book.repository;

import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.echocat.kata.java.part1.author.entity.Author;
import org.echocat.kata.java.part1.author.repository.AuthorRepository;
import org.echocat.kata.java.part1.book.entity.Book;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Component
public class BookRepository {

    private static final String RESOURCE = "src/main/resources/books.csv";
    private static final List<Book> BOOKS = new ArrayList<>();

    private final AuthorRepository authorRepository;

    public BookRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(RESOURCE));
            CSVReader csvReader = new CSVReader(reader);

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                if (!nextRecord[0].contains("title")) {
                    String[] record = nextRecord[0].split(";");
                    Book book = new Book()
                            .setTitle(record[0])
                            .setIsbn(record[1])
                            .setAuthors(findAuthors(asList(record[2].split(","))))
                            .setDescription(validateDescription(record));
                    BOOKS.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Book> findAll() {
        return BOOKS;
    }

    public Optional<Book> findByIsbn(String isbn) {
        return BOOKS.stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst();
    }

    public List<Book> findByAuthor(String email) {
        Author author = authorRepository.findByEmail(email).orElseThrow(() ->
                new ResourceAccessException(format("Connot find the author using email: (%s)", email)));

        return BOOKS.stream()
                .filter(b -> b.getAuthors().contains(author))
                .collect(toList());
    }

    private List<Author> findAuthors(List<String> emails) {
        return emails.stream()
                .map(authorRepository::findByEmail)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private String validateDescription(String[] record) {
        try {
            return record[3];
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

}
