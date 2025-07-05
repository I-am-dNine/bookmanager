package com.d9.bookmanager.controller;

import com.d9.bookmanager.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

import com.d9.bookmanager.dto.ApiResponse;
import com.d9.bookmanager.entity.Book;

@RestController
@RequestMapping("/books")
@Tag(name = "Books", description = "書籍管理相關操作")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @PreAuthorize("hasRole('admin','STAFF')")
    @Operation(summary = "查詢所有書籍", description = "取得系統中所有書籍的清單")
    public ApiResponse<List<Book>> getAllBooks() {
        return ApiResponse.success("書籍清單取得成功", bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    @Operation(summary = "查詢單一本書", description = "依照書籍 ID 查詢該書詳細資訊")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(book -> ResponseEntity.ok(ApiResponse.success("查詢成功", book)))
                .orElse(ResponseEntity.status(404).body(ApiResponse.error(404, "找不到該書籍")));
    }

    @PreAuthorize("hasRole('ADMIN','STAFF')")
    @PostMapping
    @Operation(summary = "新增書籍", description = "建立一本新書，需提供書名、作者、ISBN、庫存數量")
    public ResponseEntity<ApiResponse<Book>> createBook(@RequestBody @Valid Book book) {
        Book created = bookService.createBook(book);
        return ResponseEntity.ok(ApiResponse.success("書籍建立成功", created));
    }

    @PreAuthorize("hasRole('ADMIN','STAFF')")
    @PutMapping("/{id}")
    @Operation(summary = "更新書籍", description = "根據 ID 修改書籍的基本資訊")
    public ResponseEntity<ApiResponse<Book>> updateBook(@PathVariable Long id, @RequestBody @Valid Book book) {
        Book updated = bookService.updateBook(id, book);
        return ResponseEntity.ok(ApiResponse.success("書籍更新成功", updated));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "刪除書籍", description = "根據 ID 刪除書籍紀錄")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(ApiResponse.success("書籍已刪除", null));
    }

    @GetMapping("/search")
    @Operation(summary = "搜尋書籍", description = "可透過書名或作者關鍵字進行模糊查詢，支援分頁")
    public ApiResponse<Page<Book>> searchBooks(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Book> result = bookService.searchBooks(keyword, pageRequest);
        return ApiResponse.success("查詢成功，共 " + result.getTotalElements() + " 筆", result);
    }
}
