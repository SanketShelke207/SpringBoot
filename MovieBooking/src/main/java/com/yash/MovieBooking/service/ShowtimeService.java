package com.yash.MovieBooking.service;

import com.yash.MovieBooking.domain.Movie;
import com.yash.MovieBooking.domain.Showtime;
import com.yash.MovieBooking.domain.Seat;
import com.yash.MovieBooking.repository.ShowtimeRepository;
import com.yash.MovieBooking.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;

/**
 * Service class for managing showtime-related operations.
 */
@Service
public class ShowtimeService {

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
     * Adds a new showtime for a movie.
     * <p>
     * This method is transactional, ensuring that both the showtime and associated seats are saved
     * to the database, or none at all in case of an error. It also prevents duplicate showtimes
     * for the same movie, date, and time.
     *
     * @param movie     The {@link Movie} for which to add the showtime.
     * @param showDate  The date of the showtime.
     * @param showTime  The time of the showtime.
     * @return The saved {@link Showtime} object.
     * @throws IllegalStateException if a showtime already exists for the given movie, date, and time.
     */
    @Transactional
    public Showtime addShowtime(Movie movie, Date showDate, Date showTime) {

        // Check if a showtime already exists for this movie, date, and time
        Showtime existingShowtime = showtimeRepository.findByMovieAndShowDateAndShowTime(movie, showDate, showTime);

        if (existingShowtime != null) {
            // A showtime already exists, so don't create a new one
            throw new IllegalStateException("Showtime already exists for this movie, date, and time.");
        }

        Showtime showtime = new Showtime();
        showtime.setMovie(movie);
        showtime.setShowDate(showDate);
        showtime.setShowTime(showTime);

        Showtime savedShowtime = showtimeRepository.save(showtime); // Save the showtime first

        // Generate seats (1 to 8)
        for (int seatNumber = 1; seatNumber <= 8; seatNumber++) {
            Seat seat = new Seat();
            seat.setShowtime(savedShowtime); // Set the showtime relationship
            seat.setSeatNumber(seatNumber);
            seat.setStatus("available"); // Set the initial status
            seatRepository.save(seat); // Save the seat
        }

        return savedShowtime; // Return the saved showtime
    }

    /**
     * Retrieves a list of showtimes for a specific movie ID.
     *
     * @param movieId The ID of the movie.
     * @return A list of {@link Showtime} objects associated with the given movie ID.
     */
    public List<Showtime> getShowtimesByMovie(int movieId) {
        return showtimeRepository.findByMovie_Mid(movieId);
    }

    /**
     * Retrieves a list of all showtimes.
     *
     * @return A list of all {@link Showtime} objects.
     */
    public List<Showtime> getAllshows() {
        return showtimeRepository.findAll();
    }

    /**
     * Deletes a showtime and its associated seats.
     *
     * @param showId The ID of the showtime to delete.
     */
    @Transactional
    public void deleteShow(int showId) {
        // 1. Delete associated seats
        List<Seat> seats = seatRepository.findByShowtime_ShowtimeId(showId);
        seatRepository.deleteAll(seats);

        // 2. Delete the showtime
        showtimeRepository.deleteById(showId);
    }

    /**
     * Retrieves a showtime by its ID.
     *
     * @param showId The ID of the showtime to retrieve.
     * @return The {@link Showtime} object matching the given ID, or null if not found.
     */
    public Showtime getShowtimeById(int showId) {
        return showtimeRepository.findById(showId).orElse(null);
    }
}