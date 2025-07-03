package com.yash.MovieBooking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents a movie in the movie booking system.
 */
@Entity
@Table(name = "movie")
@Getter
@Setter
public class Movie {
    /**
     * The unique identifier for the movie.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mid")
    private int mid;

    /**
     * The name of the movie.  Cannot be null.
     */
    @Column(name = "mname", nullable = false)
    private String mname;

    /**
     * The genre of the movie.
     */
    @Column(name = "mgenre")
    private String mgenre;

    /**
     * A description of the movie.
     */
    @Column(name = "description")
    private String description;

    /**
     * The duration of the movie in minutes.
     */
    @Column(name = "duration")
    private int duration;

    /**
     * The release date of the movie.
     */
    @Column(name = "relasedate")
    private String relasedate;

    /**
     * The image associated with the movie.  Could be a file name or URL.
     */
    @Column(name = "mimage")
    private String mimage;

    /**
     * The rating of the movie (e.g., out of 5 stars).
     */
    @Column(name = "ratings")
    private double ratings;

    /**
     * The price of a ticket for the movie.
     */
    @Column(name = "price")
    private int price;

    // No direct relationship with Showtime here

    /**
     * Returns a string representation of the Movie object.  Useful for debugging.
     * @return A string representation of the Movie.
     */
    @Override
    public String toString() {
        return "Movie{" +
                "mid=" + mid +
                ", mname='" + mname + '\'' +
                ", mgenre='" + mgenre + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", relasedate='" + relasedate + '\'' +
                ", mimage='" + mimage + '\'' +
                ", ratings=" + ratings +
                ", price=" + price +
                '}';
    }
}