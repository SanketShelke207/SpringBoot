package com.yash.MovieBooking.controller;

import com.yash.MovieBooking.domain.Movie;
import com.yash.MovieBooking.domain.User;
import com.yash.MovieBooking.dto.UserDTO;
import com.yash.MovieBooking.service.BookingService;
import com.yash.MovieBooking.service.MovieService;
import com.yash.MovieBooking.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST controller for managing user related requests, including registration,
 * authentication, and user data retrieval.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    /**
     * Service for handling user data operations.
     */
    @Autowired
    private UserService userService;

    /**
     * Service for handling booking data operations.
     */
    @Autowired
    private BookingService bookingService;

    /**
     * Service for handling movie data operations.
     */
    @Autowired
    private MovieService movieService;

    /**
     * ModelMapper for converting between User entities and UserDTOs.
     */
    @Autowired
    private ModelMapper modelMapper;


    /**
     * Retrieves a list of movies for the home page.
     *
     * @param userId The ID of the user.
     * @return ResponseEntity containing a list of Movie objects.
     */
    @GetMapping("/home/{userId}")
    public ResponseEntity<List<Movie>> home(@PathVariable int userId) {
        List<Movie> movies = this.movieService.shoeMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    /**
     * Registers a new user.
     *
     * @param userDTO The UserDTO containing the user's registration information.
     * @return ResponseEntity containing the created UserDTO.
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        try {
            User user = modelMapper.map(userDTO, User.class);

            User createdUser = this.userService.createUser(user);
            UserDTO createdUserDTO = modelMapper.map(createdUser, UserDTO.class);
            return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Login (Handled by Spring Security)
    // No manual login method needed, Spring Security will authenticate users

    /**
     * Logs out the current user.  Handled by Spring Security.
     *
     * @return ResponseEntity containing a confirmation message.
     */
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        return new ResponseEntity<>("Logged out", HttpStatus.OK);
    }

    /**
     * Retrieves admin dashboard data, including total bookings and price.
     * Requires the user to be authenticated as an admin.
     *
     * @param principal The currently authenticated user.
     * @return ResponseEntity containing a map with admin dashboard information.
     */
    @GetMapping("/admin/dashboard")
    public ResponseEntity<?> getAdminDashboard(Principal principal) {
        if ("admin".equals(principal.getName())) {
            Map<String, Object> totals = bookingService.getTotalBookingsAndPrice();
            return new ResponseEntity<>(totals, HttpStatus.OK);
        }
        return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
    }

    /**
     * Retrieves the details of the currently logged-in user.
     *
     * @param principal The currently authenticated user.
     * @return ResponseEntity containing the UserDTO of the current user.
     */
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        if (user != null) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves a list of all users.  Accessible only by administrators.
     *
     * @return ResponseEntity containing a list of UserDTOs.
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    /**
     * Retrieves a specific user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity containing the UserDTO if found, or NOT_FOUND if not.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}