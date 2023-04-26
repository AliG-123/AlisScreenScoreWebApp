package com.infotel.ali.alisscreenscorewebapp.services;

import com.infotel.ali.alisscreenscorewebapp.dto.MovieDTO;
import com.infotel.ali.alisscreenscorewebapp.exceptions.MovieExceptions;
import com.infotel.ali.alisscreenscorewebapp.models.Movie;
import com.infotel.ali.alisscreenscorewebapp.models.Review;
import com.infotel.ali.alisscreenscorewebapp.repositories.MovieRepository;
import com.infotel.ali.alisscreenscorewebapp.repositories.ReviewRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private MovieRepository movieRepository;

    private ReviewRepository reviewRepository;

    @Autowired
    public MovieService(ReviewRepository reviewRepository, MovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
    }



    // Step 1: Calculate average rating for a movie
    public BigDecimal calculateAverageRating(Integer movieId) {
        // Query the database for all ratings for the given movie ID
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        if (reviews.isEmpty()) {
            return BigDecimal.ZERO;
        }

        // Calculate the average rating
        BigDecimal sum = BigDecimal.ZERO;
        for (Review review : reviews) {
            sum = sum.add(review.getRating());
        }
        BigDecimal averageRating = sum.divide(BigDecimal.valueOf(reviews.size()), 1, RoundingMode.HALF_UP);

        return averageRating;
    }

    // Step 2: Call this method whenever a new rating is added, updated, or deleted
    public void updateMovieAverageRating(Integer movieId) {
        BigDecimal averageRating = calculateAverageRating(movieId);

        // Update the Movie entity in the database with the calculated average rating
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException());
        movie.setAverageRating(averageRating);
        movieRepository.save(movie);
    }


    // Method to convert Movie entity to MovieDTO
    public MovieDTO convertToDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setCoverPhotoUrl(movie.getCoverPhotoUrl());
        movieDTO.setReleaseDate(movie.getReleaseDate());
        movieDTO.setAverageRating(movie.getAverageRating());
        return movieDTO;
    }


    // Method to get top 10 rated movies and return MovieDTO
    public List<MovieDTO> getTop10RatedMovies() {
        // Call the method to update average ratings for all movies in the database
        List<Movie> movies = movieRepository.findAll();
        for (Movie movie : movies) {
            updateMovieAverageRating(movie.getId());
        }

        // Retrieve movies from the data source sorted by averageRating in descending order
        movies = movieRepository.findTop10ByOrderByAverageRatingDesc();

        // Limit the results to top 10
        List<MovieDTO> movieDTOs = new ArrayList<>();
        for (Movie movie : movies) {
            movieDTOs.add(convertToDTO(movie));
        }
        return movieDTOs;
    }

    // Method to get all movies and return MovieDTO
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDTO> movieDTOs = new ArrayList<>();
        for (Movie movie : movies) {
            updateMovieAverageRating(movie.getId());
            movieDTOs.add(convertToDTO(movie));
        }
        return movieDTOs;
    }

    // Method to get movie by id and return MovieDTO
    public MovieDTO getMovieById(int id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            return convertToDTO(movie);
        }
        return null;
    }

    // Method to create movie and return MovieDTO
    public MovieDTO createMovie(String title, String coverPhotoUrl, LocalDate releaseDate) {
        // Check if username is already taken
        if (movieRepository.existsByTitle(title)) {
            System.out.println("Movie already exists");
            throw new MovieExceptions.MovieExistsException("Movie already exists");
        }

        // Create a new user entity
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setReleaseDate(releaseDate);
        movie.setCoverPhotoUrl(coverPhotoUrl);
        movie.setCreatedAt(Instant.now());
        movieRepository.save(movie);

        return convertToDTO(movie);
    }

    // Method to update movie and return MovieDTO
    public MovieDTO updateMovie(int id, Movie updatedMovie) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            movie.setTitle(updatedMovie.getTitle());
            movie.setReleaseDate(updatedMovie.getReleaseDate());
            movie.setCoverPhotoUrl(updatedMovie.getCoverPhotoUrl());
            Movie savedMovie = movieRepository.save(movie);
            return convertToDTO(savedMovie);
        }
        return null;
    }

    // Method to delete movie
    public void deleteMovie(int id) {
        movieRepository.deleteById(id);
    }

}



//    public String fetchMovieData(String movieId) {
//        try {
//            String API_KEY = "1d8f5cde987638eb8af5d3ef0ea91842"; // Your TMDB API key
//            // Construct API endpoint with movie ID and API key
//            String endpoint = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + API_KEY;
//
//            // Create HttpClient
//            HttpClient httpClient = HttpClientBuilder.create().build();
//            HttpGet httpGet = new HttpGet(endpoint);
//
//            // Execute HTTP request
//            HttpResponse response = httpClient.execute(httpGet);
//
//            // Get response code
//            int responseCode = response.getStatusLine().getStatusCode();
//
//            // If response code is 200 (OK), read response data
//            if (responseCode == 200) {
//                String jsonString = EntityUtils.toString(response.getEntity());
//                System.out.println("Response: " + jsonString);
//                return jsonString;
//            } else {
//                System.out.println("Request failed with response code: " + responseCode);
//                return null;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public MovieDTO fetchAndSaveMovie(String movieId) throws IOException, JSONException, InterruptedException {
//        // Call fetchMovieData method from TmdbApiClient
//        String jsonString = fetchMovieData(movieId);
//
//        // Check if response is not null
//        if (jsonString != null) {
//            JSONObject jsonObject = new JSONObject(jsonString);
//
//            // Call saveMovieFromJson method to extract and save movie data
//            return saveMovieFromJson(jsonObject);
//        } else {
//            // Handle error case
//            System.out.println("Failed to fetch movie data from TMDB API.");
//            // You can throw an exception or return an appropriate response based on your requirement
//            return null; // Or throw an exception
//        }
//    }
//
//
//
//    // Call api to get movie data
//
//    public MovieDTO saveMovieFromJson(JSONObject jsonObject) {
//        // Extract title from JSON response
//        String title = jsonObject.getString("original_title");
//
//        // Extract release date from JSON response
//        String releaseDateString = jsonObject.getString("release_date");
//        LocalDate releaseDate = LocalDate.parse(releaseDateString);
//
//        // Extract poster path from JSON response
//        String posterPath = jsonObject.getString("poster_path");
//
//        // Construct complete cover photo URL
//        String coverPhotoUrl = "https://image.tmdb.org/t/p/original" + posterPath;
//
//        // Create a new Movie entity
//        Movie movie = new Movie();
//        movie.setTitle(title);
//        movie.setReleaseDate(releaseDate);
//        movie.setCoverPhotoUrl(coverPhotoUrl);
//        movie.setCreatedAt(Instant.now());
//        movieRepository.save(movie);
//
//        // Convert and return the MovieDTO
//        return convertToDTO(movie);
//    }


