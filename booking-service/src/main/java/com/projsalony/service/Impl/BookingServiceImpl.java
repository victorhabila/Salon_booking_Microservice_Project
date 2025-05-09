package com.projsalony.service.Impl;

import com.projsalony.domain.BookingStatus;
import com.projsalony.dto.*;
import com.projsalony.model.Booking;
import com.projsalony.model.SalonReport;
import com.projsalony.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon,  Set<ServiceOfferingDTO> serviceOfferingSet
    ) throws Exception {
        int totalDuration = serviceOfferingSet.stream()
                .mapToInt(ServiceOfferingDTO::getDuration)
                .sum();

        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

//        check availability

        Boolean isSlotAvailable=isTimeSlotAvailable(salon,bookingStartTime,bookingEndTime);

        if(!isSlotAvailable){
            throw new Exception("Slot is not available");
        }

        int totalPrice = serviceOfferingSet.stream()
                .mapToInt(ServiceOfferingDTO::getPrice)
                .sum();

        Set<Long> idList = serviceOfferingSet.stream()
                .map(ServiceOfferingDTO::getId)
                .collect(Collectors.toSet());

        Booking newBooking = new Booking();
        newBooking.setCustomerId(user.getId());
        newBooking.setSalonId(salon.getId());
        newBooking.setStartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setServiceIds(idList);
        newBooking.setTotalPrice(totalPrice);
        newBooking.setStatus(BookingStatus.PENDING);

        return bookingRepository.save(newBooking);

    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long id) throws Exception {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if(booking == null){
            throw new Exception("booking not found");
        }
        return booking;

    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) throws Exception {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long salonId) {
        List<Booking> allBookings = bookingRepository.findBySalonId(salonId);

        if (date == null) {
            return allBookings;
        }
        return allBookings.stream()
                .filter(booking -> isSameDate(booking.getStartTime(), date) ||
                        isSameDate(booking.getEndTime(), date))
                .collect(Collectors.toList());

    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        List<Booking> bookings=getBookingsBySalon(salonId);

        SalonReport report = new SalonReport();


        // Total Earnings: Sum of totalPrice for all bookings
        Double totalEarnings = bookings.stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();

        // Total Bookings: Count of all bookings
        Integer totalBookings = bookings.size();

        // Cancelled Bookings: Filter bookings with status CANCELLED
        List<Booking> cancelledBookings = bookings.stream()
                .filter(booking -> booking.getStatus().toString().equalsIgnoreCase("CANCELLED"))
                .collect(Collectors.toList());

        // Refunds: Calculate based on cancelled bookings (same totalPrice as refunded)
        Double totalRefund = cancelledBookings.stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();

        report.setSalonId(salonId);
        report.setTotalEarnings(totalEarnings);
        report.setTotalBookings(totalBookings);
        report.setCancelledBookings(cancelledBookings.size());
        report.setTotalRefund(totalRefund);

        return report;

    }

    private boolean isSameDate(LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }



    public Boolean isTimeSlotAvailable(SalonDTO salon,
                                       LocalDateTime bookingStartTime,
                                       LocalDateTime bookingEndTime) throws Exception {
        List<Booking> existingBookings = getBookingsBySalon(salon.getId());

        LocalDateTime salonOpenTime = salon.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonCloseTime = salon.getCloseTime().atDate(bookingStartTime.toLocalDate());

        if (bookingStartTime.isBefore(salonOpenTime)
                || bookingEndTime.isAfter(salonCloseTime)) {
            throw new Exception("Booking time must be within salon's open hours.");
            //return false;

        }

        for (Booking existingBooking : existingBookings) {
            LocalDateTime existingStartTime = existingBooking.getStartTime();
            LocalDateTime existingEndTime = existingBooking.getEndTime();

            // Check if new booking's time overlaps with any existing booking
            if ((bookingStartTime.isBefore(existingEndTime)
                    && bookingEndTime.isAfter(existingStartTime)) ||
                    bookingStartTime.isEqual(existingStartTime) || bookingEndTime.isEqual(existingEndTime)) {
                throw new Exception("slot not available, choose different time.");
                //return false;
            }
        }
        return true;
    }
}
