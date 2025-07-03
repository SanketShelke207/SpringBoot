package com.yash.MovieBooking.controller;

import com.yash.MovieBooking.domain.Movie;
import com.yash.MovieBooking.dto.MovieDTO;
import com.yash.MovieBooking.service.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing movie related requests.
 */
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    /**
     * Service for handling movie data operations.
     */
    @Autowired
    private MovieService movieService;

    /**
     * ModelMapper for converting between Movie entities and MovieDTOs.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves a list of all movies.
     *
     * @return ResponseEntity containing a list of MovieDTOs.
     */
    @GetMapping("/all")
    public ResponseEntity<List<MovieDTO>> allmovies() {
        List<Movie> movies = movieService.shoeMovies();
        List<MovieDTO> movieDTOs = movies.stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(movieDTOs, HttpStatus.OK);
    }

    /**
     * Retrieves a specific movie by its ID.
     *
     * @param movieId The ID of the movie to retrieve.
     * @return ResponseEntity containing the MovieDTO if found, or NOT_FOUND if not.
     */
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDTO> getMovie(@PathVariable int movieId) {
        Movie movie = movieService.getMovieById(movieId);
        if (movie != null) {
            MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
            return new ResponseEntity<>(movieDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Adds a new movie.  Expects multipart form data.
     *
     * @param mname       The name of the movie.
     * @param mgenre      The genre of the movie.
     * @param description A description of the movie.
     * @param duration    The duration of the movie in minutes.
     * @param relasedate  The release date of the movie.
     * @param price       The price of the movie ticket.
     * @param ratings     The movie's rating.
     * @param movieImage  The movie's image file.
     * @return ResponseEntity containing the created MovieDTO.
     */
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MovieDTO> movieadd(
            @RequestParam("mname") String mname,
            @RequestParam("mgenre") String mgenre,
            @RequestParam("description") String description,
            @RequestParam("duration") int duration,
            @RequestParam("relasedate") String relasedate,
            @RequestParam("price") int price,
            @RequestParam("ratings") double ratings,
            @RequestParam("mimage") MultipartFile movieImage) {

        Movie movie = new Movie();
        movie.setMname(mname);
        movie.setMgenre(mgenre);
        movie.setDescription(description);
        movie.setDuration(duration);
        movie.setRelasedate(relasedate);
        movie.setRatings(ratings);
        movie.setPrice(price);
        movie.setMimage(movieImage.getOriginalFilename());

//        if (!movieImage.isEmpty()) {
//            try {
//                String uniqueFileName = movieImage.getOriginalFilename();
//                Path filePath = Paths.get("resources/images/", uniqueFileName);
//                Files.write(filePath, movieImage.getBytes());
//                movie.setMimage("/resources/images/" + uniqueFileName);
//            } catch (IOException e) {
//                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }

        Movie createdMovie = movieService.createMovie(movie);
        MovieDTO movieDTO = modelMapper.map(createdMovie, MovieDTO.class);
        return new ResponseEntity<>(movieDTO, HttpStatus.CREATED);
    }

    /**
     * Updates an existing movie.
     *
     * @param movieId      The ID of the movie to update.
     * @param movieDetails The MovieDTO containing the updated movie details.
     * @return ResponseEntity containing the updated MovieDTO if successful, or NOT_FOUND if the movie does not exist.
     */
    @PutMapping("/{movieId}")
    public ResponseEntity<MovieDTO> movieUpdate(
            @PathVariable int movieId,
            @RequestBody MovieDTO movieDetails) {

        Movie movie = movieService.getMovieById(movieId);
        if (movie == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        modelMapper.map(movieDetails, movie);  // Map DTO to entity

        Movie updatedMovie = movieService.updateMovie(movie);
        MovieDTO updatedMovieDTO = modelMapper.map(updatedMovie, MovieDTO.class);
        return new ResponseEntity<>(updatedMovieDTO, HttpStatus.OK);
    }

    /**
     * Deletes a movie.
     *
     * @param movieId The ID of the movie to delete.
     * @return ResponseEntity with NO_CONTENT on successful deletion.
     */
    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable int movieId) {
        movieService.deleteMovie(movieId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}