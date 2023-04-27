package com.infotel.ali.alisscreenscorewebapp.services;

import com.infotel.ali.alisscreenscorewebapp.dto.ReviewDTO;
import com.infotel.ali.alisscreenscorewebapp.dto.TvReviewDTO;
import com.infotel.ali.alisscreenscorewebapp.models.*;
import com.infotel.ali.alisscreenscorewebapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class TvReviewService {

    @Autowired
    private TvReviewRepository tvReviewRepository;
    private UserRepository userRepository;
    private TvShowRepository tvShowRepository;


    public TvReviewService(TvReviewRepository tvReviewRepository, UserRepository userRepository, TvShowRepository tvShowRepository) {
        this.tvReviewRepository = tvReviewRepository;
        this.userRepository = userRepository;
        this.tvShowRepository = tvShowRepository;
    }

    // Fetch reviews by movie ID
    public List<TvReviewDTO> getReviewsByTvShowId(int tvShowID) {
        List<TvReview> tvReviews = tvReviewRepository.findByTvShowId(tvShowID);
        List<TvReviewDTO> tvReviewDTOS = new ArrayList<>();
        for (TvReview tvReview : tvReviews) {
            tvReviewDTOS.add(convertToDTO(tvReview));
        }
        return tvReviewDTOS;
    }

    public TvReviewDTO createTvReview(Integer userId, int tvShowId, String reviewText, BigDecimal rating) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        TvShow tvShow = tvShowRepository.findById(tvShowId).orElseThrow(() -> new RuntimeException("Movie not found"));

        TvReview tvReview = new TvReview();
        tvReview.setUser(user);
        tvReview.setTvShow(tvShow);
        tvReview.setReviewText(reviewText);
        tvReview.setRating(rating);
        tvReview.setCreatedAt(Instant.now());
        tvReviewRepository.save(tvReview);

        return convertToDTO(tvReview) ;
    }
    public TvReviewDTO convertToDTO(TvReview tvReview) {
        TvReviewDTO tvReviewDTO = new TvReviewDTO();
        tvReviewDTO.setId(tvReview.getId());
        tvReviewDTO.setRating(tvReview.getRating());
        tvReviewDTO.setReviewText(tvReview.getReviewText());
        tvReviewDTO.setCreatedAt(tvReview.getCreatedAt());
        tvReviewDTO.setUserId(tvReview.getUser().getId());
        tvReviewDTO.setTvShowId(tvReview.getTvShow().getId());
        return tvReviewDTO;
    }
}