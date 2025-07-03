package com.yash.MovieBooking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents a booking in the movie booking system.
 */
@Entity
@Table(name = "bookings")
@Getter
@Setter
public class Booking {
    /**
     * The unique identifier for the booking.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId")
    private int bId;

    /**
     * The user who made the booking.
     */
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    /**
     * The showtime for which the booking was made.
     */
    @ManyToOne
    @JoinColumn(name = "showtimeId")
    private Showtime showtime;

    /**
     * The seat reserved in this booking - represents one seat if multiple are not selected.
     */
    @ManyToOne
    @JoinColumn(name = "seatId")
    private Seat seat;

    /**
     * The total amount charged for the booking.
     */
    @Column(name = "totalAmount")
    private double totalAmount;

    /**
     * The ID of the movie being booked.
     */
    @Column(name = "mid")
    private int mid; // Add movie ID column

    /**
     * The list of selected seat IDs for this booking, allows for multiple seats to be booked
     * within one booking record.
     */
    @ElementCollection // Use @ElementCollection for List<Integer>
    @CollectionTable(name = "booking_selected_seats", joinColumns = @JoinColumn(name = "bookingId"))
    @Column(name = "seatId")
    private List<Integer> selectedSeatIds;
}