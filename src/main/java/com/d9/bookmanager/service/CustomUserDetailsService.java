package com.d9.bookmanager.service;

import com.d9.bookmanager.entity.Reader;
import com.d9.bookmanager.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Reader reader = readerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return User.builder()
                .username(reader.getEmail())
                .password(reader.getPassword())
                .roles(reader.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .build();
    }
}
