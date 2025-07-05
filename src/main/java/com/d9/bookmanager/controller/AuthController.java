package com.d9.bookmanager.controller;

import com.d9.bookmanager.dto.ApiResponse;
import com.d9.bookmanager.dto.UserLoginRequest;
import com.d9.bookmanager.util.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody UserLoginRequest request) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        authManager.authenticate(authInputToken); // 若帳密錯誤會拋例外

        String token = jwtUtil.generateToken(request.getUsername());
        return ApiResponse.success("登入成功", token);
    }
}