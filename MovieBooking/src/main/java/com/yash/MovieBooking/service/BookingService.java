package com.yash.MovieBooking.service;

import com.yash.MovieBooking.domain.Booking;
import com.yash.MovieBooking.domain.Seat;
import com.yash.MovieBooking.repository.BookingRepository;
import com.yash.MovieBooking.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for managing booking-related operations.
 */
@Service
public class BookingService {

    /**
     * Repository for accessing and managing {@link Booking} entities.
     */
    @Autowired
    private BookingRepository bookingRepository;

    /**
     * Repository for accessing and managing {@link Seat} entities.
     */
    @Autowired
    private SeatRepository seatRepository;

    /**
     * Retrieves a list of seats for a given showtime ID.
     *
     * @param showtimeId The ID of the showtime for which to retrieve seats.
     * @return A list of {@link Seat} objects associated with the given showtime ID,
     *         or an empty list if no seats are found.
     */
    public List<Seat> getSeats(int showtimeId) {
        return seatRepository.findByShowtime_ShowtimeId(showtimeId);
    }


    /**
     * Saves a booking to the database and updates the status of the selected seats to "booked".
     *
     * @param booking The {@link Booking} object to save.
     * @return The saved {@link Booking} object.
     */
    @Transactional
    public Booking saveBookings(Booking booking) {
        // Save the booking first
        Booking savedBooking = bookingRepository.save(booking);

        // Update the status of the selected seats to "booked"
        List<Integer> selectedSeatIds = booking.getSelectedSeatIds();
        System.out.println(selectedSeatIds);
        if (selectedSeatIds != null && !selectedSeatIds.isEmpty()) {
            for (Integer seatId : selectedSeatIds) {
                Seat seat = seatRepository.findById(seatId).orElse(null);
                if (seat != null) {
                    seat.setStatus("booked");
                    seatRepository.save(seat);
                }
            }
        }

        return savedBooking;
    }

    /**
     * Retrieves a list of bookings for a specific user ID.  Ensures that the
     * associated {@link Booking#//selectedSeatIds} collection is loaded within the
     * transaction context.
     *
     * @param userId The ID of the user.
     * @return A list of {@link Booking} objects associated with the given user ID.
     */
    @Transactional
    public List<Booking> getBookingbyuserId(int userId) {
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            throw new TransactionSystemException("No transaction active!");
        }
        return bookingRepository.findByUser_Id(userId);
    }

    /**
     * Deletes a booking by its ID.
     *
     * @param bookingId The ID of the booking to delete.
     */
    public void deleteBooking(int bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    /**
     * Calculates the total number of bookings and the total price of all bookings.
     *
     * @return A map containing the total number of bookings and the total price.
     */
    public Map<String, Object> getTotalBookingsAndPrice() {
        List<Booking> allBookings = bookingRepository.findAll();
        int totalBookings = allBookings.size();
        double totalPrice = allBookings.stream().mapToDouble(Booking::getTotalAmount).sum();

        Map<String, Object> totals = new HashMap<>();
        totals.put("totalBookings", totalBookings);
        totals.put("totalPrice", totalPrice);
        return totals;
    }
}