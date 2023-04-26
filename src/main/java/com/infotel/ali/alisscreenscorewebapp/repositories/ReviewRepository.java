package com.infotel.ali.alisscreenscorewebapp.repositories;

import com.infotel.ali.alisscreenscorewebapp.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByMovieId(int movieId);
}