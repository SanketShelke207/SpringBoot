package com.yash.MovieBooking.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Data Transfer Object (DTO) representing a showtime.
 */
@Getter
@Setter
public class ShowtimeDTO {
    /**
     * The unique identifier for the showtime.
     */
    private int showtimeId;
    /**
     * The ID of the movie being shown during this showtime. Using the ID instead of the
     * full Movie object for simplicity and to avoid circular dependencies.
     */
    private int movieId;  // Use movieId instead of Movie object
    /**
     * The date of the showtime.
     */
    private Date showDate;
    /**
     * The time of the showtime.
     */
    private Date showTime;
}