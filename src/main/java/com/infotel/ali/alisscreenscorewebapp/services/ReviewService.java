package com.infotel.ali.alisscreenscorewebapp.services;

import com.infotel.ali.alisscreenscorewebapp.dto.ReviewDTO;
import com.infotel.ali.alisscreenscorewebapp.models.Movie;
import com.infotel.ali.alisscreenscorewebapp.models.Review;
import com.infotel.ali.alisscreenscorewebapp.models.User;
import com.infotel.ali.alisscreenscorewebapp.repositories.MovieRepository;
import com.infotel.ali.alisscreenscorewebapp.repositories.ReviewRepository;
import com.infotel.ali.alisscreenscorewebapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    private MovieRepository movieRepository;

    public ReviewService(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    // Fetch reviews by movie ID
    public List<ReviewDTO> getReviewsByMovieId(int movieId) {
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (Review review : reviews) {
            reviewDTOs.add(convertToDTO(review));
        }
        return reviewDTOs;
    }

    public ReviewDTO createReview(Integer userId, int movieId, String reviewText, BigDecimal rating) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));

        Review review = new Review();
        review.setUser(user);
        review.setMovie(movie);
        review.setReviewText(reviewText);
        review.setRating(rating);
        review.setCreatedAt(Instant.now());
        reviewRepository.save(review);

        return convertToDTO(review) ;
    }
    public ReviewDTO convertToDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setReviewText(review.getReviewText());
        reviewDTO.setCreatedAt(review.getCreatedAt());
        reviewDTO.setUserId(review.getUser().getId());
        reviewDTO.setMovieId(review.getMovie().getId());
        return reviewDTO;
    }
}