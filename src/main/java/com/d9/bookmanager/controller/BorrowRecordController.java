package com.d9.bookmanager.controller;

import com.d9.bookmanager.entity.BorrowRecord;
import com.d9.bookmanager.service.BorrowRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;

    public BorrowRecordController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }

    @GetMapping
    public List<BorrowRecord> getAll() {
        return borrowRecordService.getAllRecords();
    }

    @PostMapping
    public BorrowRecord create(@RequestParam Long bookId, @RequestParam Long readerId) {
        return borrowRecordService.createBorrowRecord(bookId, readerId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        borrowRecordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-reader/{readerId}")
    public List<BorrowRecord> getByReader(@PathVariable Long readerId) {
        return borrowRecordService.getRecordsByReader(readerId);
    }

    @GetMapping("/by-book/{bookId}")
    public List<BorrowRecord> getByBook(@PathVariable Long bookId) {
        return borrowRecordService.getRecordsByBook(bookId);
    }
}
