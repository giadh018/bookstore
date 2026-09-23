package fi.haagahelia.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.BookRepository;
import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.domain.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {

    private static final Logger log =
            LoggerFactory.getLogger(BookstoreApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(
            BookRepository bookRepository,
            CategoryRepository categoryRepository) {

        return (args) -> {
            if (bookRepository.count() == 0) {
                Category fiction = new Category("Fiction");
                Category classics = new Category("Classics");

                categoryRepository.save(fiction);
                categoryRepository.save(classics);

                Book firstBook = new Book(
                    "A Farewell to Arms",
                    "Ernest Hemingway",
                    1929,
                    "1232323-21",
                    15.90
                );
                firstBook.setCategory(classics);
                bookRepository.save(firstBook);

                Book secondBook = new Book(
                    "Animal Farm",
                    "George Orwell",
                    1945,
                    "2212343-5",
                    12.50
                );
                secondBook.setCategory(fiction);
                bookRepository.save(secondBook);
            }

            log.info("fetch all books");

            for (Book book : bookRepository.findAll()) {
                log.info("Book id={}, title={}",
                        book.getId(), book.getTitle());
            }
        };
    }
}