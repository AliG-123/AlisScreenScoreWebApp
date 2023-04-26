package com.infotel.ali.alisscreenscorewebapp.repositories;

import com.infotel.ali.alisscreenscorewebapp.models.Movie;
import com.infotel.ali.alisscreenscorewebapp.models.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TvShowRepository extends JpaRepository<TvShow, Integer> {

    List<TvShow> findTop10ByOrderByAverageRatingDesc();
    boolean existsByTitleAndReleaseDate(String title, LocalDate releaseDate);
    boolean existsByTitle(String title);

}