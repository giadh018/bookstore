package fi.haagahelia.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

    private static final Logger log =
            LoggerFactory.getLogger(BookstoreApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookRepository repository) {
        return (args) -> {
            repository.save(new Book(
                "A Farewell to Arms", "Ernest Hemingway", 1929,
                "9780684801469", 15.90
            ));

            repository.save(new Book(
                "Animal Farm", "George Orwell", 1945,
                "9780451526342", 12.50
            ));

            log.info("fetch all books");

            for (Book book : repository.findAll()) {
                log.info("Book id={}, title={}",
                        book.getId(), book.getTitle());
            }
        };
    }
}