package com.infotel.ali.alisscreenscorewebapp.repositories;

import com.infotel.ali.alisscreenscorewebapp.models.Review;
import com.infotel.ali.alisscreenscorewebapp.models.TvReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TvReviewRepository extends JpaRepository<TvReview, Integer> {
    List<TvReview> findByTvShowId(int TvShowID);
}