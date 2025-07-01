package com.d9.bookmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "書名不可為空")
    private String title;

    @NotBlank(message = "作者不可為空")
    private String author;

    @NotBlank(message = "ISBN 不可為空")
    private String isbn;

    @NotNull(message = "庫存不可為空")
    private Integer stock;
}
