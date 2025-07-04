package com.d9.bookmanager.controller;

import com.d9.bookmanager.dto.ApiResponse;
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
    public ApiResponse<List<Reader>> getAllReaders() {
        List<Reader> readers = readerService.getAllReaders();
        return ApiResponse.success("查詢成功，共 " + readers.size() + " 位借閱者", readers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "查詢單一借閱者", description = "透過借閱者 ID 查詢詳細資料")
    public ResponseEntity<ApiResponse<Reader>> getReaderById(@PathVariable Long id) {
        return readerService.getReaderById(id)
                .map(reader -> ResponseEntity.ok(ApiResponse.success("查詢成功", reader)))
                .orElse(ResponseEntity.status(404).body(ApiResponse.error(404, "找不到該借閱者")));
    }

    @PostMapping
    @Operation(summary = "新增借閱者", description = "新增一位新的使用者（姓名 + Email）")
    public ResponseEntity<ApiResponse<Reader>> createReader(@RequestBody @Valid Reader reader) {
        Reader created = readerService.createReader(reader);
        return ResponseEntity.ok(ApiResponse.success("借閱者新增成功", created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新借閱者資訊", description = "根據 ID 更新借閱者資料")
    public ResponseEntity<ApiResponse<Reader>> updateReader(@PathVariable Long id, @RequestBody @Valid Reader reader) {
        Reader updated = readerService.updateReader(id, reader);
        return ResponseEntity.ok(ApiResponse.success("借閱者資訊更新成功", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "刪除借閱者", description = "根據 ID 刪除借閱者紀錄")
    public ResponseEntity<ApiResponse<Void>> deleteReader(@PathVariable Long id) {
        readerService.deleteReader(id);
        return ResponseEntity.ok(ApiResponse.success("借閱者已刪除", null));
    }
}