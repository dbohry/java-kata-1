package org.echocat.kata.java.part1.magazine.repository;

import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.echocat.kata.java.part1.author.entity.Author;
import org.echocat.kata.java.part1.author.repository.AuthorRepository;
import org.echocat.kata.java.part1.magazine.entity.Magazine;
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
public class MagazineRepository {

    private static final String RESOURCE = "src/main/resources/magazines.csv";
    private static final List<Magazine> MAGAZINES = new ArrayList<>();

    private final AuthorRepository authorRepository;

    public MagazineRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(RESOURCE));
            CSVReader csvReader = new CSVReader(reader);

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                if (!nextRecord[0].contains("title")) {
                    String[] record = nextRecord[0].split(";");
                    Magazine magazine = new Magazine()
                            .setTitle(record[0])
                            .setIsbn(record[1])
                            .setAuthors(findAuthors(asList(record[2].split(","))))
                            .setPublishedAt(validatePublishedAt(record));
                    MAGAZINES.add(magazine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Magazine> findAll() {
        return MAGAZINES;
    }

    public Optional<Magazine> findByIsbn(String isbn) {
        return MAGAZINES.stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst();
    }

    public List<Magazine> findByAuthor(String email) {
        Author author = authorRepository.findByEmail(email).orElseThrow(() ->
                new ResourceAccessException(format("Connot find the author using email: (%s)", email)));

        return MAGAZINES.stream()
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

    private String validatePublishedAt(String[] record) {
        try {
            return record[3];
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

}
