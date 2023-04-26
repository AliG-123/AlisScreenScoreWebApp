package com.infotel.ali.alisscreenscorewebapp.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class ReviewDTO {
    private Integer id;
    private BigDecimal rating;
    private String reviewText;
    private Instant createdAt;
    private Integer userId;
    private Integer movieId;

    // Constructors

    public ReviewDTO() {
    }

    public ReviewDTO(Integer id, BigDecimal rating, String reviewText, Instant createdAt, Integer userId, Integer movieId) {
        this.id = id;
        this.rating = rating;
        this.reviewText = reviewText;
        this.createdAt = createdAt;
        this.userId = userId;
        this.movieId = movieId;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
}
