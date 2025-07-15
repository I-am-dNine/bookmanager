package com.d9.bookmanager.controller;

import com.d9.bookmanager.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secure")
@SecurityRequirement(name = "bearerAuth")
public class SecureTestController {

    @GetMapping("/test")
    @Operation(summary = "私有測試 API", description = "需要 JWT 驗證，測試用")
    @PreAuthorize("isAuthenticated()") // 任何登入用戶都能訪問
    public ResponseEntity<ApiResponseDto<String>> testSecureEndpoint() {
        return ResponseEntity.ok(ApiResponseDto.success("你已成功通過 JWT 驗證", "This is a protected endpoint"));
    }
}

