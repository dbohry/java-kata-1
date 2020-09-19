package org.echocat.kata.java.part1.author.repository;

import com.opencsv.CSVReader;
import org.echocat.kata.java.part1.author.entity.Author;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorRepository {

    private static final String RESOURCE = "src/main/resources/authors.csv";
    private static final List<Author> AUTHORS = new ArrayList<>();

    public AuthorRepository() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(RESOURCE));
            CSVReader csvReader = new CSVReader(reader);

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                if (!nextRecord[0].contains("title")) {
                    String[] record = nextRecord[0].split(";");
                    Author author = new Author()
                            .setEmail(record[0])
                            .setFirstName(record[1])
                            .setLastName(record[2]);
                    AUTHORS.add(author);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Author> findByEmail(String email) {
        return AUTHORS.stream()
                .filter(a -> a.getEmail().equals(email))
                .findFirst();
    }

}
