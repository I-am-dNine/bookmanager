package com.d9.bookmanager.init;

import com.d9.bookmanager.entity.Book;
import com.d9.bookmanager.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        if (bookRepository.count() == 0) {
            bookRepository.save(Book.builder()
                    .title("Effective Java")
                    .author("Joshua Bloch")
                    .isbn("9780134685991")
                    .stock(10)
                    .build());

            bookRepository.save(Book.builder()
                    .title("Clean Code")
                    .author("Robert C. Martin")
                    .isbn("9780132350884")
                    .stock(5)
                    .build());

            bookRepository.save(Book.builder()
                    .title("Spring in Action")
                    .author("Craig Walls")
                    .isbn("9781617294945")
                    .stock(7)
                    .build());

            bookRepository.save(Book.builder()
                    .title("Head First Design Patterns")
                    .author("Eric Freeman")
                    .isbn("9780596007126")
                    .stock(3)
                    .build());

            bookRepository.save(Book.builder()
                    .title("Java Concurrency in Practice")
                    .author("Brian Goetz")
                    .isbn("9780321349606")
                    .stock(4)
                    .build());

            System.out.println("📚 Sample books initialized.");
        }
    }
}
