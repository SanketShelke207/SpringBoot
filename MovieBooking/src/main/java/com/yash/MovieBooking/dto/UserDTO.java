package com.yash.MovieBooking.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a user.
 */
@Getter
@Setter
public class UserDTO {
    /**
     * The unique identifier for the user.
     */
    private int id;
    /**
     * The name of the user (used as the username).
     */
    private String name;
    /**
     * The email address of the user.
     */
    private String email;
    /**
     * The phone number of the user.
     */
    private String phoneno;
    /**
     * The address of the user.
     */
    private String address;
    /**
     * The password of the user.  Include this field *only* when needed (e.g., for registration).
     * Exclude it from responses to avoid security risks.
     */
    private String password;  // Include if needed for registration, exclude for responses
}