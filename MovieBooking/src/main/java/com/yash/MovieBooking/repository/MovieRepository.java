package com.yash.MovieBooking.repository;

import com.yash.MovieBooking.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Movie} entities in the database.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    /**
     * Retrieves a list of movies belonging to a specific genre.
     *
     * @param genre The genre of the movies to retrieve.
     * @return A list of {@link Movie} objects matching the given genre.
     */
    List<Movie> findByMgenre(String genre);
}