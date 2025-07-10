package com.d9.bookmanager.controller;

import com.d9.bookmanager.dto.ApiResponse;
import com.d9.bookmanager.dto.JwtResponse;
import com.d9.bookmanager.dto.LoginRequest;
import com.d9.bookmanager.dto.RegisterRequest;
import com.d9.bookmanager.entity.Reader;
import com.d9.bookmanager.security.JwtTokenUtil;
import com.d9.bookmanager.service.ReaderService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ReaderService readerService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, ReaderService readerService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.readerService = readerService;
    }

    @PostMapping("/login")
    @Operation(summary = "使用者登入", description = "輸入 Email 與密碼取得 JWT Token")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(ApiResponse.success("登入成功", new JwtResponse(token)));
    }

    @PostMapping("/register")
    @Operation(summary = "使用者註冊", description = "提供名稱、Email 與密碼註冊新帳號")
    public ResponseEntity<ApiResponse<Reader>> register(@Valid @RequestBody RegisterRequest request) {
        Reader newReader = readerService.registerNewReader(request);
        return ResponseEntity.ok(ApiResponse.success("註冊成功", newReader));
    }
    
}