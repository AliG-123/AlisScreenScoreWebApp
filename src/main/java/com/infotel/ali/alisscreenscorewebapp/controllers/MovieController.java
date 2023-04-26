package com.infotel.ali.alisscreenscorewebapp.controllers;

import com.infotel.ali.alisscreenscorewebapp.dto.MovieCreateDTO;
import com.infotel.ali.alisscreenscorewebapp.dto.MovieDTO;
import com.infotel.ali.alisscreenscorewebapp.dto.UserDTO;
import com.infotel.ali.alisscreenscorewebapp.dto.UserRegistrationDTO;
import com.infotel.ali.alisscreenscorewebapp.exceptions.MovieExceptions;
import com.infotel.ali.alisscreenscorewebapp.exceptions.RegistrationExceptions;
import com.infotel.ali.alisscreenscorewebapp.repositories.MovieRepository;
import com.infotel.ali.alisscreenscorewebapp.services.MovieService;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;
    private MovieRepository movieRepository;

    @GetMapping("/movies")
    public List<MovieDTO> getMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/movies/top-rated")
    public ResponseEntity<List<MovieDTO>> getTopRatedMovies() {
        List<MovieDTO> top10Movies = movieService.getTop10RatedMovies();
        if (top10Movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(top10Movies);
        }
    }

    @GetMapping("/movies/{id}")
    public MovieDTO getMovies(@PathVariable int id) {
        return movieService.getMovieById(id);
    }

    @PostMapping("/movie/add")
    public ResponseEntity<?> addMovie(@RequestBody MovieCreateDTO movieCreateDTO) {
        try {
            MovieDTO movieDTO = movieService.createMovie(movieCreateDTO.getTitle(),movieCreateDTO.getCoverPhotoUrl(),movieCreateDTO.getReleaseDate());
            return ResponseEntity.ok(movieDTO);
        } catch (MovieExceptions.MovieExistsException ex) {
            return ResponseEntity.badRequest().body("Movie already exists");
        }
    }
//    @PostMapping("/fetch/{movieId}")
//    public MovieDTO fetchAndSaveMovie(@PathVariable String movieId) throws IOException, JSONException, InterruptedException {
//        return movieService.fetchAndSaveMovie(movieId);
//    }

}
