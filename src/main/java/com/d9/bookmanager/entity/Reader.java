package com.d9.bookmanager.entity;

import java.util.HashSet;
import java.util.Set;

import com.d9.bookmanager.model.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "readers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
}
