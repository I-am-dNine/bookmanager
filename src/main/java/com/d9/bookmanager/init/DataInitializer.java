package com.d9.bookmanager.init;

import com.d9.bookmanager.entity.Book;
import com.d9.bookmanager.entity.BorrowRecord;
import com.d9.bookmanager.entity.Reader;
import com.d9.bookmanager.repository.BookRepository;
import com.d9.bookmanager.repository.BorrowRecordRepository;
import com.d9.bookmanager.repository.ReaderRepository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

        private final BookRepository bookRepository;
        private final ReaderRepository readerRepository;
        private final BorrowRecordRepository borrowRecordRepository;

        public DataInitializer(BookRepository bookRepository,
                                ReaderRepository readerRepository,
                                BorrowRecordRepository borrowRecordRepository) {
                this.bookRepository = bookRepository;
                this.readerRepository = readerRepository;
                this.borrowRecordRepository = borrowRecordRepository;
        }

    @Override
    public void run(String... args) {
        // 初始化書籍
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

        // 初始化借閱者
        if (readerRepository.count() == 0) {
                readerRepository.save(Reader.builder()
                                .name("Alice Lin")
                                .email("alice@example.com")
                                .build());

                readerRepository.save(Reader.builder()
                                .name("Bob Chen")
                                .email("bob@example.com")
                                .build());

                readerRepository.save(Reader.builder()
                                .name("Cathy Wu")
                                .email("cathy@example.com")
                                .build());

                System.out.println("👤 Sample readers initialized.");
        }

        // 初始化借閱記錄
        if (borrowRecordRepository.count() == 0) {
                List<Book> books = bookRepository.findAll();
                List<Reader> readers = readerRepository.findAll();
    
                borrowRecordRepository.saveAll(List.of(
                    BorrowRecord.builder()
                            .book(books.get(0))
                            .reader(readers.get(0))
                            .borrowDate(LocalDate.of(2025, 6, 1))
                            .returnDate(null)
                            .build(),
                    BorrowRecord.builder()
                            .book(books.get(1))
                            .reader(readers.get(1))
                            .borrowDate(LocalDate.of(2025, 6, 3))
                            .returnDate(LocalDate.of(2025, 6, 10))
                            .build()
                ));
    
                System.out.println("📖 Sample borrow records initialized.");
        }
    }
}
