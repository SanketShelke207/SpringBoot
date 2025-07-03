package com.yash.MovieBooking.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a booking.
 */
@Getter
@Setter
public class BookingDTO {
    /**
     * The unique identifier for the booking.
     */
    private int bookingId;
    /**
     * The ID of the user who made the booking.
     */
    private int userId;
    /**
     * The ID of the showtime for which the booking was made.
     */
    private int showtimeId;
    /**
     * The list of selected seat IDs for this booking.
     */
    private List<Integer> selectedSeatIds;  // Add this line
    /**
     * The total amount charged for the booking.
     */
    private double totalAmount;
    /**
     * The ID of the movie being booked.
     */
    private int mid;
}