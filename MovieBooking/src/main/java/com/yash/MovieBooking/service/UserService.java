package com.yash.MovieBooking.service;

import com.yash.MovieBooking.domain.User;
import com.yash.MovieBooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing user-related operations.
 */
@Service
public class UserService {

    /**
     * Repository for accessing and managing {@link User} entities.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Password encoder for encrypting user passwords.
     */
    @Autowired
    private PasswordEncoder passwordEncoder; // Inject the PasswordEncoder

    /**
     * Creates a new user.
     * <p>
     * This method encodes the user's password before saving it to the database.
     *
     * @param user The {@link User} object to create.
     * @return The created {@link User} object.
     */
    public User createUser(User user) {
        // Encode the password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    /**
     * Finds a user by their username (name).
     *
     * @param name The username (name) of the user to find.
     * @return The {@link User} object matching the given username, or null if not found.
     */
    public User findUserByUsername(String name) {
        return userRepository.findByName(name);
    }

    /**
     * Retrieves a list of all users.
     *
     * @return A list of all {@link User} objects.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The {@link User} object matching the given ID, or null if not found.
     */
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }
}