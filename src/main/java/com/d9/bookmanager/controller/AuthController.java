package com.d9.bookmanager.controller;

import com.d9.bookmanager.dto.ApiResponseDto;
import com.d9.bookmanager.dto.JwtResponse;
import com.d9.bookmanager.dto.RegisterRequest;
import com.d9.bookmanager.dto.UserLoginRequest;
import com.d9.bookmanager.entity.Reader;
import com.d9.bookmanager.repository.ReaderRepository;
import com.d9.bookmanager.security.JwtTokenUtil;
import com.d9.bookmanager.service.JwtBlacklistService;
import com.d9.bookmanager.service.ReaderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "登入 / 註冊 / Token 發行")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ReaderRepository readerRepository;
    private final JwtBlacklistService jwtBlacklistService;
    private final ReaderService readerService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenUtil jwtTokenUtil,
                          ReaderRepository readerRepository,
                          JwtBlacklistService jwtBlacklistService,
                          ReaderService readerService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.readerRepository = readerRepository;
        this.jwtBlacklistService = jwtBlacklistService;
        this.readerService = readerService;
    }

    @PostMapping("/login")
    @Operation(summary = "使用者登入", description = "輸入 email 和 password，成功後會回傳 JWT token")
    public ResponseEntity<ApiResponseDto<JwtResponse>> login(@Valid @RequestBody UserLoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(ApiResponseDto.success("登入成功", new JwtResponse(token)));
    }

    @PostMapping("/register")
    @Operation(summary = "使用者註冊", description = "建立新帳號，預設角色為 USER")
    public ResponseEntity<ApiResponseDto<String>> register(@Valid @RequestBody RegisterRequest request) {
        if (readerRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(400, "帳號已存在"));
        }
        readerService.registerNewReader(request);
        return ResponseEntity.ok(ApiResponseDto.success("註冊成功", null));
    }

    @GetMapping("/me")
    @Operation(summary = "取得目前登入者資訊")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponseDto<Reader>> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return readerRepository.findByEmail(userDetails.getUsername())
                .map(reader -> ResponseEntity.ok(ApiResponseDto.success("取得成功", reader)))
                .orElse(ResponseEntity.badRequest().body(ApiResponseDto.error(404, "找不到使用者")));
    }

    @PostMapping("/logout")
    @Operation(summary = "登出", description = "將 JWT 加入黑名單")
    public ResponseEntity<ApiResponseDto<Void>> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            long expiration = jwtTokenUtil.getExpiration(token);
            long now = System.currentTimeMillis();
            long ttl = (expiration - now) / 1000; // 秒
            if (ttl > 0) {
                jwtBlacklistService.blacklistToken(token, ttl);
            }
        }
        return ResponseEntity.ok(ApiResponseDto.success("登出成功", null));
    }
    
}
