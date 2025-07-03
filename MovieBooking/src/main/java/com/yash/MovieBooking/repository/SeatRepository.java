package com.yash.MovieBooking.repository;

import com.yash.MovieBooking.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Seat} entities in the database.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 */
@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    /**
     * Retrieves a list of seats associated with a specific showtime ID.
     *
     * @param showtimeId The ID of the showtime.
     * @return A list of {@link Seat} objects for the given showtime.
     */
    List<Seat> findByShowtime_ShowtimeId(int showtimeId);
}