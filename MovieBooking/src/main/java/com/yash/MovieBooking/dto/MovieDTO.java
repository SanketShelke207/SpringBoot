package com.yash.MovieBooking.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a movie.
 */
@Getter
@Setter
public class MovieDTO {
    /**
     * The unique identifier for the movie.
     */
    private int mid;
    /**
     * The name of the movie.
     */
    private String mname;
    /**
     * The genre of the movie.
     */
    private String mgenre;
    /**
     * A description of the movie.
     */
    private String description;
    /**
     * The duration of the movie in minutes.
     */
    private int duration;
    /**
     * The release date of the movie.
     */
    private String relasedate;
    /**
     * The image associated with the movie.
     */
    private String mimage;
    /**
     * The rating of the movie.
     */
    private double ratings;
    /**
     * The price of a ticket for the movie.
     */
    private int price;
}