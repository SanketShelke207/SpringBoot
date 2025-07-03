package com.yash.MovieBooking.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a booking request. This DTO is used
 * to receive booking data from the client.
 */
@Getter
@Setter
public class BookingRequestDTO {

    /**
     * The ID of the user making the booking request.
     */
    private int userId;

    /**
     * The ID of the showtime for which the booking is requested.
     */
    private int showtimeId;

    /**
     * The list of selected seat IDs for which the booking is requested.
     */
    private List<Integer> selectedSeatIds;  // Add this line

    /**
     * The total amount for the booking request.
     */
    private double totalAmount;

    /**
     * The ID of the movie for which the booking is requested.
     */
    private int mid;

    // Getters and setters
}