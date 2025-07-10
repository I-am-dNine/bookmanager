package com.d9.bookmanager.controller;

import com.d9.bookmanager.dto.ApiResponseDto;
import com.d9.bookmanager.dto.RegisterRequest;
import com.d9.bookmanager.entity.Reader;
import com.d9.bookmanager.model.Role;
import com.d9.bookmanager.repository.ReaderRepository;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "登入與註冊")
public class AuthController {

    private final ReaderRepository readerRepository;

    public AuthController(
            ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Operation(
        summary = "使用者註冊",
        description = "註冊新使用者帳號，email 為登入用帳號",
        responses = {
            @ApiResponse(responseCode = "200", description = "註冊成功"),
            @ApiResponse(responseCode = "400", description = "帳號已存在或格式錯誤")
        }
    )
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<String>> register(@Valid @RequestBody RegisterRequest request) {
        if (readerRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                .body(ApiResponseDto.error(400, "該 email 已被註冊"));
        }

        Reader reader = Reader.builder()
            .email(request.getEmail())
            .name(request.getName())
            // .password(passwordEncoder.encode(request.getPassword()))
            .roles(Set.of(Role.USER))
            .build();

        readerRepository.save(reader);

        return ResponseEntity.ok(ApiResponseDto.success("註冊成功", null));
    }
}