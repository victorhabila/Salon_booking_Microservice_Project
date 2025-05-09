package com.projsalony.service.Impl;

import com.projsalony.domain.BookingStatus;
import com.projsalony.dto.*;
import com.projsalony.model.Booking;
import com.projsalony.model.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking (BookingRequest booking, UserDTO user, SalonDTO salon, Set<ServiceOfferingDTO> serviceOfferingDTOSet) throws Exception;

    List<Booking> getBookingsByCustomer( Long customerId);

    List<Booking> getBookingsBySalon(Long salonID);

    Booking getBookingById(Long id) throws Exception;
    Booking updateBooking(Long bookingId, BookingStatus status) throws Exception;

    List<Booking> getBookingsByDate(LocalDate date, Long salonId);
    SalonReport getSalonReport (Long salonId);

}
