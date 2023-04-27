package com.infotel.ali.alisscreenscorewebapp.controllers;

import com.infotel.ali.alisscreenscorewebapp.dto.MovieCreateDTO;
import com.infotel.ali.alisscreenscorewebapp.dto.MovieDTO;
import com.infotel.ali.alisscreenscorewebapp.dto.TvShowCreateDTO;
import com.infotel.ali.alisscreenscorewebapp.dto.TvShowDTO;
import com.infotel.ali.alisscreenscorewebapp.exceptions.MovieExceptions;
import com.infotel.ali.alisscreenscorewebapp.repositories.MovieRepository;
import com.infotel.ali.alisscreenscorewebapp.repositories.TvShowRepository;
import com.infotel.ali.alisscreenscorewebapp.services.MovieService;
import com.infotel.ali.alisscreenscorewebapp.services.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TvShowController {

    @Autowired
    private TvShowService tvShowService;
    private TvShowRepository tvShowRepository;

    @GetMapping("/tvshows")
    public List<TvShowDTO> getTvShows() {return tvShowService.getAllTvShows();}

    @GetMapping("/tvshows/top-rated")
    public ResponseEntity<List<TvShowDTO>> getTopRatedMovies() {
        List<TvShowDTO> top10Movies = tvShowService.getTop10RatedMovies();
        if (top10Movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(top10Movies);
        }
    }

    @GetMapping("/tvshows/{id}")
    public TvShowDTO getTvShow(@PathVariable int id) {
        return tvShowService.getTvShowById(id);
    }

    @PostMapping("/tvshows/add")
    public ResponseEntity<?> addTvShow(@RequestBody TvShowCreateDTO tvShowCreateDTO) {
        try {
            TvShowDTO tvShowDTO = tvShowService.createTvShow(tvShowCreateDTO.getTitle(),tvShowCreateDTO.getCoverPhotoUrl(),tvShowCreateDTO.getReleaseDate());
            return ResponseEntity.ok(tvShowDTO);
        } catch (MovieExceptions.MovieExistsException ex) {
            return ResponseEntity.badRequest().body("Movie already exists");
        }
    }
//    @PostMapping("/fetch/{movieId}")
//    public MovieDTO fetchAndSaveMovie(@PathVariable String movieId) throws IOException, JSONException, InterruptedException {
//        return movieService.fetchAndSaveMovie(movieId);
//    }

}
