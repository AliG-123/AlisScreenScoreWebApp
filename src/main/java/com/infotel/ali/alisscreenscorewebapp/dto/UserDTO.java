package com.infotel.ali.alisscreenscorewebapp.dto;

import java.time.Instant;

public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private Instant createdAt;

    private String role;


    // Default constructor
    public UserDTO() {}

    // Constructor with fields
    public UserDTO(Integer id, String username, String email, Instant createdAt, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", role='" + role + '\'' +
                '}';
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}
