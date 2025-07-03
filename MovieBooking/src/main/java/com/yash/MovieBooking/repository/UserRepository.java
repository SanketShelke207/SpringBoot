package com.yash.MovieBooking.repository;

import com.yash.MovieBooking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link User} entities in the database.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Retrieves a user by their name.
     *
     * @param name The name of the user to retrieve.
     * @return The {@link User} object matching the given name, or null if not found.
     */
    User findByName(String name);
}