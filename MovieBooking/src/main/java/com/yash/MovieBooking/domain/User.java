package com.yash.MovieBooking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a user in the movie booking system.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * The name of the user. Cannot be null.  Used as the username for authentication.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The user's phone number.
     */
    @Column(name = "phoneno")
    private String phoneno;

    /**
     * The user's email address.
     */
    @Column(name = "email")
    private String email;

    /**
     * The user's address.
     */
    @Column(name = "address")
    private String address;

    /**
     * The user's password. Cannot be null.
     */
    @Column(name = "password", nullable = false)
    private String password;

    // Getters and setters (Lombok's @Getter and @Setter)
    /**
     * Returns a string representation of the User object, useful for debugging.
     * @return A string representation of the User.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneno='" + phoneno + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}