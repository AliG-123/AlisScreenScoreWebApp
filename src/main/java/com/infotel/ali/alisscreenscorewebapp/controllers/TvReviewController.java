package com.infotel.ali.alisscreenscorewebapp.controllers;

import com.infotel.ali.alisscreenscorewebapp.dto.ReviewCreateDTO;
import com.infotel.ali.alisscreenscorewebapp.dto.ReviewDTO;
import com.infotel.ali.alisscreenscorewebapp.dto.TvReviewCreateDTO;
import com.infotel.ali.alisscreenscorewebapp.dto.TvReviewDTO;
import com.infotel.ali.alisscreenscorewebapp.services.ReviewService;
import com.infotel.ali.alisscreenscorewebapp.services.TvReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class TvReviewController {
    @Autowired
    private TvReviewService tvReviewService;

    @GetMapping("/tvshows/{tvShowId}/reviews")
    public List<TvReviewDTO> getReviewsByTvShowId(@PathVariable int tvShowId) {
        return tvReviewService.getReviewsByTvShowId(tvShowId);
    }

    @PostMapping("/tvshows/{tvShowId}/reviews/create")
    public ResponseEntity<TvReviewDTO> createTvReview(@PathVariable int tvShowId, @RequestBody TvReviewCreateDTO tvReviewCreateDTO) {
        // Extract the parameters from the request body
        Integer userId = tvReviewCreateDTO.getUserId();
        tvShowId = tvReviewCreateDTO.getMovieId();
        String reviewText = tvReviewCreateDTO.getReviewText();
        BigDecimal rating = tvReviewCreateDTO.getRating();

        // Call the createReview method in the ReviewService
        TvReviewDTO tvReviewDTO = tvReviewService.createTvReview(userId, tvShowId, reviewText, rating);

        // Return the created review as the response with a status of 201 Created
        return new ResponseEntity<>(tvReviewDTO, HttpStatus.CREATED);
    }

}

