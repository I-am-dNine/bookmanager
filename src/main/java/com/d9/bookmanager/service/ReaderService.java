package com.d9.bookmanager.service;

import com.d9.bookmanager.dto.RegisterRequest;
import com.d9.bookmanager.entity.Reader;
import com.d9.bookmanager.model.Role;
import com.d9.bookmanager.repository.ReaderRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;

@Service
public class ReaderService {

    private final ReaderRepository readerRepository;
    private final PasswordEncoder passwordEncoder;


    public ReaderService(ReaderRepository readerRepository, PasswordEncoder passwordEncoder) {
        this.readerRepository = readerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    public Optional<Reader> getReaderById(Long id) {
        return readerRepository.findById(id);
    }

    public Optional<Reader> getReaderByIdWithCheck(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        boolean isAdminOrStaff = authentication.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN") || auth.getAuthority().equals("ROLE_STAFF"));
        Optional<Reader> readerOpt = readerRepository.findById(id);
        if (readerOpt.isEmpty()) {
            return Optional.empty();
        }
        Reader reader = readerOpt.get();
        if (!isAdminOrStaff && !reader.getEmail().equals(currentUsername)) {
            throw new AccessDeniedException("You can only access your own information");
        }
        return Optional.of(reader);
    }

    public Reader createReader(Reader reader) {
        return readerRepository.save(reader);
    }

    public Reader updateReader(Long id, Reader newReader) {
        Reader reader = readerRepository.findById(id).orElseThrow();
        reader.setName(newReader.getName());
        reader.setEmail(newReader.getEmail());
        return readerRepository.save(reader);
    }

    public void deleteReader(Long id) {
        readerRepository.deleteById(id);
    }

    public Reader registerNewReader(RegisterRequest request) {
        if (readerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email 已被註冊");
        }
    
        Reader reader = Reader.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .roles(Set.of(Role.USER)) // 預設角色
            .build();
    
        return readerRepository.save(reader);
    }
    
    public Reader updateReaderWithCheck(Long id, Reader reader) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        boolean isAdminOrStaff = authentication.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN") || auth.getAuthority().equals("ROLE_STAFF"));
        Optional<Reader> existingOpt = readerRepository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Reader not found");
        }
        Reader existing = existingOpt.get();
        if (!isAdminOrStaff && !existing.getEmail().equals(currentUsername)) {
            throw new AccessDeniedException("You can only update your own information");
        }
        return updateReader(id, reader);
    }
}
