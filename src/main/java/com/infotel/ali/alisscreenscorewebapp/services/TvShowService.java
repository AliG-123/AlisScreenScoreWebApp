package com.infotel.ali.alisscreenscorewebapp.services;

import com.infotel.ali.alisscreenscorewebapp.dto.TvShowDTO;
import com.infotel.ali.alisscreenscorewebapp.exceptions.MovieExceptions;
import com.infotel.ali.alisscreenscorewebapp.models.Review;
import com.infotel.ali.alisscreenscorewebapp.models.TvReview;
import com.infotel.ali.alisscreenscorewebapp.models.TvShow;
import com.infotel.ali.alisscreenscorewebapp.repositories.ReviewRepository;
import com.infotel.ali.alisscreenscorewebapp.repositories.TvReviewRepository;
import com.infotel.ali.alisscreenscorewebapp.repositories.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TvShowService {
    @Autowired
    private TvShowRepository tvShowRepository;

    private TvReviewRepository tvReviewRepository;


    public TvShowService(TvShowRepository tvShowRepository, TvReviewRepository tvReviewRepository) {
        this.tvShowRepository = tvShowRepository;
        this.tvReviewRepository = tvReviewRepository;
    }



    // Step 1: Calculate average rating for a movie
    public BigDecimal calculateAverageRating(Integer id) {
        // Query the database for all ratings for the given movie ID
        List<TvReview> tvReviews = tvReviewRepository.findByTvShowId(id);
        if (tvReviews.isEmpty()) {
            return BigDecimal.ZERO;
        }

        // Calculate the average rating
        BigDecimal sum = BigDecimal.ZERO;
        for (TvReview tvReview : tvReviews) {
            sum = sum.add(tvReview.getRating());
        }
        BigDecimal averageRating = sum.divide(BigDecimal.valueOf(tvReviews.size()), 1, RoundingMode.HALF_UP);

        return averageRating;
    }

    // Step 2: Call this method whenever a new rating is added, updated, or deleted
    public void updateTvShowAverageRating(Integer id) {
        BigDecimal averageRating = calculateAverageRating(id);

        // Update the Movie entity in the database with the calculated average rating
        TvShow tvShow = tvShowRepository.findById(id).orElseThrow(() -> new RuntimeException());
        tvShow.setAverageRating(averageRating);
        tvShowRepository.save(tvShow);
    }


    // Method to convert Movie entity to MovieDTO
    public TvShowDTO convertToDTO(TvShow tvShow) {
        TvShowDTO tvShowDTO = new TvShowDTO();
        tvShowDTO.setId(tvShow.getId());
        tvShowDTO.setTitle(tvShow.getTitle());
        tvShowDTO.setCoverPhotoUrl(tvShow.getCoverPhotoUrl());
        tvShowDTO.setReleaseDate(tvShow.getReleaseDate());
        tvShowDTO.setAverageRating(tvShow.getAverageRating());
        return tvShowDTO;
    }


    // Method to get top 10 rated movies and return MovieDTO
    public List<TvShowDTO> getTop10RatedMovies() {
        // Call the method to update average ratings for all movies in the database
        List<TvShow> tvShows = tvShowRepository.findAll();
        for (TvShow tvShow : tvShows) {
            updateTvShowAverageRating(tvShow.getId());
        }

        // Retrieve movies from the data source sorted by averageRating in descending order
        tvShows = tvShowRepository.findTop10ByOrderByAverageRatingDesc();

        // Limit the results to top 10
        List<TvShowDTO> tvShowDTOS = new ArrayList<>();
        for (TvShow tvShow : tvShows) {
            tvShowDTOS.add(convertToDTO(tvShow));
        }
        return tvShowDTOS;
    }

    // Method to get all movies and return MovieDTO
    public List<TvShowDTO> getAllTvShows() {
        List<TvShow> tvShows = tvShowRepository.findAll();
        List<TvShowDTO> tvShowDTOS = new ArrayList<>();
        for (TvShow tvShow : tvShows) {
            updateTvShowAverageRating(tvShow.getId());
            tvShowDTOS.add(convertToDTO(tvShow));
        }
        return tvShowDTOS;
    }

    // Method to get movie by id and return MovieDTO
    public TvShowDTO getTvShowById(int id) {
        Optional<TvShow> optionalTvShow = tvShowRepository.findById(id);
        if (optionalTvShow.isPresent()) {
            TvShow tvShow = optionalTvShow.get();
            return convertToDTO(tvShow);
        }
        return null;
    }

    // Method to create movie and return MovieDTO
    public TvShowDTO createTvShow(String title, String coverPhotoUrl, LocalDate releaseDate) {
        // Check if username is already taken
        if (tvShowRepository.existsByTitle(title)) {
            System.out.println("TvShow already exists");
            throw new MovieExceptions.MovieExistsException("TvShow already exists");
        }

        // Create a new user entity
        TvShow tvShow = new TvShow();
        tvShow.setTitle(title);
        tvShow.setReleaseDate(releaseDate);
        tvShow.setCoverPhotoUrl(coverPhotoUrl);
        tvShow.setCreatedAt(Instant.now());
        tvShowRepository.save(tvShow);

        return convertToDTO(tvShow);
    }
}
