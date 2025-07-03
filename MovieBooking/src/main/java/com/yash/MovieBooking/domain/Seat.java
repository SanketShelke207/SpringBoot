package com.yash.MovieBooking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a seat in the movie booking system.
 */
@Entity
@Table(name = "seats")
@Getter
@Setter
public class Seat {
    /**
     * The unique identifier for the seat.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seatId")
    private int seatId;

    /**
     * The showtime to which this seat belongs.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "showtimeId")
    private Showtime showtime;

    /**
     * The number of the seat within the showtime.
     */
    @Column(name = "seatNumber")
    private int seatNumber;

    /**
     * The current status of the seat (e.g., "available", "booked", "unavailable").
     */
    @Column(name = "status")
    private String status;
}