package com.yash.MovieBooking.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a seat.
 */
@Getter
@Setter
public class SeatDTO {
    /**
     * The unique identifier for the seat.
     */
    private int seatId;
    /**
     * The ID of the showtime to which this seat belongs.  Using the ID instead of the
     * full Showtime object for simplicity and to avoid circular dependencies.
     */
    private int showtimeId; // Use showtimeId instead of Showtime object
    /**
     * The number of the seat within the showtime.
     */
    private int seatNumber;
    /**
     * The current status of the seat (e.g., "available", "booked").
     */
    private String status;
}