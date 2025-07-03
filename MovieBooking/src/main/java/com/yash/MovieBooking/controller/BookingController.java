package com.yash.MovieBooking.controller;

import com.yash.MovieBooking.domain.Booking;
import com.yash.MovieBooking.domain.Movie;
import com.yash.MovieBooking.domain.Seat;
import com.yash.MovieBooking.domain.Showtime;
import com.yash.MovieBooking.dto.BookingDTO;
import com.yash.MovieBooking.dto.BookingRequestDTO;
import com.yash.MovieBooking.dto.SeatDTO;
import com.yash.MovieBooking.dto.ShowtimeDTO;
import com.yash.MovieBooking.service.BookingService;
import com.yash.MovieBooking.service.MovieService;
import com.yash.MovieBooking.service.ShowtimeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST controller for handling movie booking related requests.
 */
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    /**
     * Service for managing showtime data.
     */
    @Autowired
    private ShowtimeService showtimeService;

    /**
     * Service for managing booking operations.
     */
    @Autowired
    private BookingService bookingService;

    /**
     * Service for managing movie data.
     */
    @Autowired
    private MovieService movieService;

    /**
     * ModelMapper for converting between DTOs and entities.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves available showtimes for a given movie.
     *
     * @param mid The ID of the movie.
     * @return ResponseEntity containing a list of ShowtimeDTOs.
     */
    @GetMapping("/showtimes")
    public ResponseEntity<List<ShowtimeDTO>> book(@RequestParam("movieId") int mid) {
        List<Showtime> showtimes = this.showtimeService.getShowtimesByMovie(mid);
        List<ShowtimeDTO> showtimeDTOs = showtimes.stream()
                .map(showtime -> {
                    ShowtimeDTO showtimeDTO = modelMapper.map(showtime, ShowtimeDTO.class);
                    showtimeDTO.setMovieId(showtime.getMovie().getMid()); // Set movieId in DTO
                    return showtimeDTO;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(showtimeDTOs, HttpStatus.OK);
    }

    /**
     * Retrieves available seats for a given showtime.
     *
     * @param showtimeId The ID of the showtime.
     * @return ResponseEntity containing a list of SeatDTOs.
     */
    @GetMapping("/seats")
    public ResponseEntity<List<SeatDTO>> bookings(@RequestParam("showtimeId") int showtimeId) {
        List<Seat> seats = this.bookingService.getSeats(showtimeId);
        if (seats == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Or appropriate error
        }

        List<SeatDTO> seatDTOs = seats.stream()
                .map(seat -> {
                    SeatDTO seatDTO = modelMapper.map(seat, SeatDTO.class);
                    seatDTO.setShowtimeId(seat.getShowtime().getShowtimeId()); // Set showtimeId in DTO
                    return seatDTO;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(seatDTOs, HttpStatus.OK);
    }

    /**
     * Processes a booking request.
     *
     * @param bookingRequestDTO The booking request data.
     * @return ResponseEntity containing the saved BookingDTO.
     */
    @PostMapping("/process")
    public ResponseEntity<BookingDTO> processBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        Booking booking = modelMapper.map(bookingRequestDTO, Booking.class);  // Map to entity
        Booking savedBooking = bookingService.saveBookings(booking);
        BookingDTO savedBookingDTO = modelMapper.map(savedBooking, BookingDTO.class);  // Map back to DTO
        return new ResponseEntity<>(savedBookingDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves bookings for a specific user.
     *
     * @param userId The ID of the user.
     * @return ResponseEntity containing a list of BookingDTOs.
     */
    @GetMapping("/view/{userId}")
    public ResponseEntity<List<BookingDTO>> getBokingbyuid(@PathVariable int userId) {
        List<Booking> bookings = this.bookingService.getBookingbyuserId(userId);
        List<BookingDTO> bookingDTOs = bookings.stream()
                .map(booking -> {
                    BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);
                    return bookingDTO;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookingDTOs, HttpStatus.OK);
    }

    /**
     * Deletes a booking.
     *
     * @param bookingId The ID of the booking to delete.
     * @return ResponseEntity with no content.
     */
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> delete(@PathVariable int bookingId) {
        this.bookingService.deleteBooking(bookingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves admin dashboard data.
     *
     * @return ResponseEntity containing a map with admin dashboard information.
     */
    @GetMapping("/adminboard")
    public ResponseEntity<Map<String, Object>> adminDashboard() {
        Map<String, Object> totals = bookingService.getTotalBookingsAndPrice();
        return new ResponseEntity<>(totals, HttpStatus.OK);
    }
}