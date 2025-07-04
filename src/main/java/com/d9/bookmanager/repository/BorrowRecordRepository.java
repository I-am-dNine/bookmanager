package com.d9.bookmanager.repository;

import com.d9.bookmanager.entity.Book;
import com.d9.bookmanager.entity.BorrowRecord;
import com.d9.bookmanager.entity.Reader;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByReader(Reader reader);
    List<BorrowRecord> findByBook(Book book);
}
