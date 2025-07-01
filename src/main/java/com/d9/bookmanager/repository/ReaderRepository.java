package com.d9.bookmanager.repository;

import com.d9.bookmanager.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
}
