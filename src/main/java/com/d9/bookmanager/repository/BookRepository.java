package com.d9.bookmanager.repository;

import com.d9.bookmanager.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    // 不需要写任何方法，JPA 会自动提供 CRUD 方法
}
