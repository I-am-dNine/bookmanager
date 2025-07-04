package com.d9.bookmanager.controller;

import com.d9.bookmanager.entity.Reader;
import com.d9.bookmanager.service.ReaderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/readers")
@Tag(name = "Readers", description = "借閱者資料管理操作")
public class ReaderController {

    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping
    @Operation(summary = "查詢所有借閱者", description = "取得所有使用者（借閱人）清單")
    public List<Reader> getAllReaders() {
        return readerService.getAllReaders();
    }

    @GetMapping("/{id}")
    @Operation(summary = "查詢單一借閱者", description = "透過借閱者 ID 查詢詳細資料")
    public ResponseEntity<Reader> getReaderById(@PathVariable Long id) {
        return readerService.getReaderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "新增借閱者", description = "新增一位新的使用者（姓名 + Email）")
    public Reader createReader(@RequestBody @Valid Reader reader) {
        return readerService.createReader(reader);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新借閱者資訊", description = "根據 ID 更新借閱者資料")
    public ResponseEntity<Reader> updateReader(@PathVariable Long id, @RequestBody @Valid Reader reader) {
        return ResponseEntity.ok(readerService.updateReader(id, reader));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "刪除借閱者", description = "根據 ID 刪除借閱者紀錄")
    public ResponseEntity<Void> deleteReader(@PathVariable Long id) {
        readerService.deleteReader(id);
        return ResponseEntity.noContent().build();
    }
}