package com.infotel.ali.alisscreenscorewebapp.dto;

import java.math.BigDecimal;

public class ReviewCreateDTO {
    private Integer userId;
    private Integer movieId;
    private String reviewText;
    private BigDecimal rating;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public BigDecimal getRating() {
        return rating;
    }
}
