package com.infotel.ali.alisscreenscorewebapp.controllers;

import com.infotel.ali.alisscreenscorewebapp.dto.ReviewCreateDTO;
import com.infotel.ali.alisscreenscorewebapp.dto.ReviewDTO;
import com.infotel.ali.alisscreenscorewebapp.models.Review;
import com.infotel.ali.alisscreenscorewebapp.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/movies/{movieId}/reviews")
    public List<ReviewDTO> getReviewsByMovieId(@PathVariable int movieId) {
        return reviewService.getReviewsByMovieId(movieId);
    }

    @PostMapping("/movie/{movieId}/reviews/create")
    public ResponseEntity<ReviewDTO> createReview(@PathVariable int movieId, @RequestBody ReviewCreateDTO reviewCreateDTO) {
        // Extract the parameters from the request body
        Integer userId = reviewCreateDTO.getUserId();
        movieId = reviewCreateDTO.getMovieId();
        String reviewText = reviewCreateDTO.getReviewText();
        BigDecimal rating = reviewCreateDTO.getRating();

        // Call the createReview method in the ReviewService
        ReviewDTO reviewDTO = reviewService.createReview(userId, movieId, reviewText, rating);

        // Return the created review as the response with a status of 201 Created
        return new ResponseEntity<>(reviewDTO, HttpStatus.CREATED);
    }

}

