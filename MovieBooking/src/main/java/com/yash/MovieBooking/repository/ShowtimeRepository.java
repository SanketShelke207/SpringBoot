package com.yash.MovieBooking.repository;

import com.yash.MovieBooking.domain.Movie;
import com.yash.MovieBooking.domain.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repository interface for managing {@link Showtime} entities in the database.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 */
@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Integer> {

    /**
     * Retrieves a list of showtimes for a specific movie ID.
     *
     * @param movieId The ID of the movie.
     * @return A list of {@link Showtime} objects associated with the given movie ID.
     */
    List<Showtime> findByMovie_Mid(int movieId);

    /**
     * Retrieves a showtime based on the movie, show date, and show time.
     *
     * @param movie    The movie.
     * @param showDate The show date.
     * @param showTime The show time.
     * @return The {@link Showtime} object matching the given criteria, or null if not found.
     */
    Showtime findByMovieAndShowDateAndShowTime(Movie movie, Date showDate, Date showTime);
}