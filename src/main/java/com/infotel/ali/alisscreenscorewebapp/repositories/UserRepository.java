package com.infotel.ali.alisscreenscorewebapp.repositories;

import com.infotel.ali.alisscreenscorewebapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.stream.DoubleStream;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Check if username exists
    boolean existsByUsername(String username);

    // Check if email exists
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);

}