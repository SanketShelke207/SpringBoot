package com.yash.MovieBooking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Represents a showtime for a movie in the movie booking system.
 */
@Entity
@Table(name = "showtime")
@Getter
@Setter
public class Showtime {
    /**
     * The unique identifier for the showtime.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showtimeId")
    private int showtimeId;

    /**
     * The movie being shown during this showtime.
     * <p>
     * Removed cascade = CascadeType.ALL because `Showtime` should not handle `Movie` deletion.
     */
    @ManyToOne  // Removed cascade = CascadeType.ALL
    @JoinColumn(name = "mid")
    private Movie movie;

    /**
     * The date of the showtime.
     */
    @Column(name = "showDate")
    private Date showDate;

    /**
     * The time of the showtime.
     */
    @Column(name = "showTime")
    private Date showTime;
}