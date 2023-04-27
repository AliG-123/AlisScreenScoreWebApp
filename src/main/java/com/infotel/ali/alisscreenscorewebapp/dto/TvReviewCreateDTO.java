package com.infotel.ali.alisscreenscorewebapp.dto;

import java.math.BigDecimal;

public class TvReviewCreateDTO {
    private Integer userId;
    private Integer tvShowId;
    private String reviewText;
    private BigDecimal rating;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setMovieId(Integer movieId) {
        this.tvShowId = movieId;
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
        return tvShowId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public BigDecimal getRating() {
        return rating;
    }
}
