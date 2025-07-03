package com.yash.MovieBooking.controller;

import com.yash.MovieBooking.domain.Movie;
import com.yash.MovieBooking.domain.Showtime;
import com.yash.MovieBooking.dto.ShowtimeDTO;
import com.yash.MovieBooking.service.MovieService;
import com.yash.MovieBooking.service.ShowtimeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing showtime related requests.
 */
@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {

    /**
     * Service for handling showtime data operations.
     */
    @Autowired
    private final ShowtimeService showtimeService;

    /**
     * Service for handling movie data operations.
     */
    @Autowired
    private final MovieService movieService;

    /**
     * ModelMapper for converting between Showtime entities and ShowtimeDTOs.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Constructor for ShowtimeController, injecting dependencies.
     *
     * @param showtimeService The ShowtimeService.
     * @param movieService    The MovieService.
     * @param modelMapper     The ModelMapper.
     */
    public ShowtimeController(ShowtimeService showtimeService, MovieService movieService, ModelMapper modelMapper) {
        this.showtimeService = showtimeService;
        this.movieService = movieService;
        this.modelMapper = modelMapper;
    }

    /**
     * Placeholder endpoint for seats.
     *
     * @return ResponseEntity containing a string "seats".
     */
    @GetMapping("/seats")
    public ResponseEntity<String> seats() {
        return new ResponseEntity<>("seats", HttpStatus.OK);
    }


    /**
     * Retrieves a list of all showtimes.
     *
     * @return ResponseEntity containing a list of ShowtimeDTOs.
     */
    @GetMapping("/all")
    public ResponseEntity<List<ShowtimeDTO>> allshow() {
        List<Showtime> showtimes = this.showtimeService.getAllshows();
        List<ShowtimeDTO> showtimeDTOs = showtimes.stream()
                .map(showtime -> modelMapper.map(showtime, ShowtimeDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(showtimeDTOs, HttpStatus.OK);
    }

    /**
     * Adds a new showtime.
     *
     * @param mid         The ID of the movie.
     * @param showDateStr The show date as a string (yyyy-MM-dd).
     * @param showTimeStr The show time as a string (HH:mm).
     * @return ResponseEntity containing the created ShowtimeDTO, or an error message.
     */
    @PostMapping(path = "/add")
    public ResponseEntity<?> addShowtime(
            @RequestParam("mid") int mid,
            @RequestParam("showDate") String showDateStr,
            @RequestParam("showTime") String showTimeStr) {

        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

            Date showDate = dateFormatter.parse(showDateStr);
            Date showTime = timeFormatter.parse(showTimeStr);

            Movie movie = movieService.getMovieById(mid);

            if (movie != null) {
                try {
                    Showtime showtime = showtimeService.addShowtime(movie, showDate, showTime);
                    ShowtimeDTO showtimeDTO = modelMapper.map(showtime, ShowtimeDTO.class);
                    return new ResponseEntity<>(showtimeDTO, HttpStatus.CREATED); // Return created showtime

                } catch (IllegalStateException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // Status code is correct
                } catch (Exception e) {
                    return new ResponseEntity<>("Error: Some error occured!", HttpStatus.CONFLICT); // Status code is wrong
                }

            } else {
                return new ResponseEntity<>("Invalid movie selection!", HttpStatus.BAD_REQUEST); // Status code is wrong
            }

        } catch (ParseException e) {
            return new ResponseEntity<>("Invalid date/time format!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves a list of showtimes for a specific movie.
     *
     * @param movieId The ID of the movie.
     * @return ResponseEntity containing a list of ShowtimeDTOs.
     */
    @GetMapping("/list/{movieId}")
    public ResponseEntity<List<ShowtimeDTO>> getShowtimesByMovie(@PathVariable int movieId) {
        List<Showtime> showtimes = showtimeService.getShowtimesByMovie(movieId);
        List<ShowtimeDTO> showtimeDTOs = showtimes.stream()
                .map(showtime -> {
                    ShowtimeDTO showtimeDTO = modelMapper.map(showtime, ShowtimeDTO.class);
                    showtimeDTO.setMovieId(showtime.getMovie().getMid()); // Set movieId in DTO
                    return showtimeDTO;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(showtimeDTOs, HttpStatus.OK);
    }

    /**
     * Deletes a showtime.
     *
     * @param showId The ID of the showtime to delete.
     * @return ResponseEntity with NO_CONTENT on successful deletion.
     */
    @DeleteMapping("/{showId}")
    public ResponseEntity<Void> deleteShow(@PathVariable int showId) {
        showtimeService.deleteShow(showId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}