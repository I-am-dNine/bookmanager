package com.d9.bookmanager.service;

import com.d9.bookmanager.entity.Book;
import com.d9.bookmanager.entity.BorrowRecord;
import com.d9.bookmanager.entity.Reader;
import com.d9.bookmanager.repository.BookRepository;
import com.d9.bookmanager.repository.BorrowRecordRepository;
import com.d9.bookmanager.repository.ReaderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository,
                               BookRepository bookRepository,
                               ReaderRepository readerRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    public List<BorrowRecord> getAllRecords() {
        return borrowRecordRepository.findAll();
    }

    public BorrowRecord createBorrowRecord(Long bookId, Long readerId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("找不到書籍"));
        Reader reader = readerRepository.findById(readerId).orElseThrow(() -> new RuntimeException("找不到借閱者"));

        BorrowRecord record = BorrowRecord.builder()
                .book(book)
                .reader(reader)
                .borrowDate(LocalDate.now())
                .returnDate(null)
                .build();

        return borrowRecordRepository.save(record);
    }

    public void deleteRecord(Long id) {
        borrowRecordRepository.deleteById(id);
    }

    public List<BorrowRecord> getRecordsByReader(Long readerId) {
        Reader reader = readerRepository.findById(readerId).orElseThrow(() -> new RuntimeException("找不到使用者"));
        return borrowRecordRepository.findByReader(reader);
    }
    
    public List<BorrowRecord> getRecordsByBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("找不到書籍"));
        return borrowRecordRepository.findByBook(book);
    }
}
