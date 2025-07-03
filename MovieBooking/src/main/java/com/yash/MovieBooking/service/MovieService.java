package com.yash.MovieBooking.service;

import com.yash.MovieBooking.domain.Movie;
import com.yash.MovieBooking.domain.Showtime;
import com.yash.MovieBooking.domain.Seat;
import com.yash.MovieBooking.repository.MovieRepository;
import com.yash.MovieBooking.repository.ShowtimeRepository;
import com.yash.MovieBooking.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing movie-related operations.
 */
@Service
public class MovieService {

    /**
     * Repository for accessing and managing {@link Movie} entities.
     */
    @Autowired
    private MovieRepository movieRepository;

    /**
     * Repository for accessing and managing {@link Showtime} entities.
     */
    @Autowired
    private ShowtimeRepository showtimeRepository;

    /**
     * Repository for accessing and managing {@link Seat} entities.
     */
    @Autowired
    private SeatRepository seatRepository;

    /**
     * Creates a new movie.
     *
     * @param movie The {@link Movie} object to create.
     * @return The created {@link Movie} object.
     */
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    /**
     * Retrieves a movie by its ID.
     *
     * @param mid The ID of the movie to retrieve.
     * @return The {@link Movie} object matching the given ID, or null if not found.
     */
    public Movie getMovieById(int mid) {
        return movieRepository.findById(mid).orElse(null);
    }

    /**
     * Retrieves a list of movies belonging to a specific genre.
     *
     * @param genre The genre of the movies to retrieve.
     * @return A list of {@link Movie} objects matching the given genre.
     */
    public List<Movie> getMovieBygenre(String genre) {
        return movieRepository.findByMgenre(genre);
    }

    /**
     * Retrieves a list of all movies.
     *
     * @return A list of all {@link Movie} objects.
     */
    public List<Movie> shoeMovies() {
        return movieRepository.findAll();
    }

    /**
     * Deletes a movie by its ID.  Also deletes any associated showtimes and seats to
     * maintain data integrity.
     *
     * @param mid The ID of the movie to delete.
     */
    @Transactional
    public void deleteMovie(int mid) {
        // 1. Get associated showtimes
        List<Showtime> showtimes = showtimeRepository.findByMovie_Mid(mid);

        // 2. Iterate through showtimes and delete associated seats
        for (Showtime showtime : showtimes) {
            List<Seat> seats = seatRepository.findByShowtime_ShowtimeId(showtime.getShowtimeId());
            seatRepository.deleteAll(seats);
        }

        // 3. Delete the showtimes
        showtimeRepository.deleteAll(showtimes);

        // 4. Delete the movie
        movieRepository.deleteById(mid);
    }

    /**
     * Updates an existing movie.
     *
     * @param movie The {@link Movie} object to update.
     * @return The updated {@link Movie} object.
     */
    public Movie updateMovie(Movie movie) {
        return movieRepository.save(movie);
    }
}