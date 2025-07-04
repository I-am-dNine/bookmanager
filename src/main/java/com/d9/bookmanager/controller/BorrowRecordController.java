package com.d9.bookmanager.controller;

import com.d9.bookmanager.entity.BorrowRecord;
import com.d9.bookmanager.service.BorrowRecordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
@Tag(name = "BorrowRecords", description = "書籍借閱紀錄操作")
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;

    public BorrowRecordController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }

    @GetMapping
    @Operation(summary = "查詢所有借閱紀錄", description = "取得所有借閱資料，包括書籍與借閱者資訊")
    public List<BorrowRecord> getAll() {
        return borrowRecordService.getAllRecords();
    }

    @PostMapping
    @Operation(summary = "建立借閱紀錄", description = "指定書籍與使用者，建立新的借閱記錄")
    public BorrowRecord create(@RequestParam Long bookId, @RequestParam Long readerId) {
        return borrowRecordService.createBorrowRecord(bookId, readerId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "刪除借閱紀錄", description = "根據借閱紀錄 ID 刪除該筆資料")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        borrowRecordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-reader/{readerId}")
    @Operation(summary = "查詢某使用者的借閱紀錄", description = "依據 readerId 查詢他借過哪些書")
    public List<BorrowRecord> getByReader(@PathVariable Long readerId) {
        return borrowRecordService.getRecordsByReader(readerId);
    }

    @GetMapping("/by-book/{bookId}")
    @Operation(summary = "查詢某本書的借閱紀錄", description = "依據 bookId 查詢誰借過這本書")
    public List<BorrowRecord> getByBook(@PathVariable Long bookId) {
        return borrowRecordService.getRecordsByBook(bookId);
    }
}
