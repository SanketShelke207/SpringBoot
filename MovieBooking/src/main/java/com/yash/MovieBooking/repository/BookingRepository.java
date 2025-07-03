package com.yash.MovieBooking.repository;

import com.yash.MovieBooking.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;

/**
 * Repository interface for managing {@link Booking} entities in the database.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    /**
     * Retrieves a list of bookings associated with a specific user ID, eagerly fetching the selected seat IDs.
     * <p>
     * The {@link EntityGraph} annotation specifies that the `selectedSeatIds` collection should be fetched
     * eagerly, preventing lazy initialization issues.
     *
     * @param userId The ID of the user.
     * @return A list of {@link Booking} objects associated with the given user ID.
     */
    @EntityGraph(attributePaths = {"selectedSeatIds"})
    List<Booking> findByUser_Id(int userId);
}