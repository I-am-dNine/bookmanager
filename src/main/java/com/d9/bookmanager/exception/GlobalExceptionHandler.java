package com.d9.bookmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.d9.bookmanager.dto.ApiResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 全域例外處理器，統一回傳格式
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // 處理表單驗證錯誤
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(400, "欄位驗證失敗", errors));
    }

    // 處理一般 Runtime 錯誤（可擴充其他類型）
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(500, "服务器发生错误：" + ex.getMessage()));
    }

}
