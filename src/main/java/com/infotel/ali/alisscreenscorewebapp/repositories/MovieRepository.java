package com.infotel.ali.alisscreenscorewebapp.repositories;

import com.infotel.ali.alisscreenscorewebapp.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
//    Optional<Movie> findByTitle(String title);
//
//    List<Movie> findAllByOrderByReleaseDateAsc();
//
//    List<Movie> findByReleaseDateYear(int year);
    List<Movie> findTop10ByOrderByAverageRatingDesc();
    boolean existsByTitleAndReleaseDate(String title, LocalDate releaseDate);
    boolean existsByTitle(String title);
}
