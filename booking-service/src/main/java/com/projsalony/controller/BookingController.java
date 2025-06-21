package com.projsalony.controller;

import com.projsalony.domain.BookingStatus;
import com.projsalony.dto.*;
import com.projsalony.mapper.BookingMapper;
import com.projsalony.model.Booking;
import com.projsalony.model.SalonReport;
import com.projsalony.service.Impl.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;
   // private final SalonService salonService;


    //private final UserFeignClient userService;
    //private final SalonFeignClient salonService;
    //private final ServiceOfferingFeignClient serviceOfferingService;
    //private final PaymentFeignClient paymentService;
    //private final UserFeignClient userFeignClient;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @PostMapping()
    public ResponseEntity<Booking> createBooking(
            @RequestParam Long salonId,
            @RequestBody BookingRequest bookingRequest) throws Exception {


       UserDTO user = new UserDTO();
       user.setId(1L);

       SalonDTO salon = new SalonDTO();
       salon.setId(salonId);
       salon.setOpenTime(LocalTime.now());
       salon.setCloseTime(LocalTime.now().plusHours(12));

       Set<ServiceOfferingDTO> serviceOfferingDTOSet = new HashSet<>();

        ServiceOfferingDTO serviceOfferingDTO = new ServiceOfferingDTO();
        serviceOfferingDTO.setId(1L);
        serviceOfferingDTO.setPrice(399);
        serviceOfferingDTO.setDuration(45);
        serviceOfferingDTO.setName("Hair cut");
       serviceOfferingDTOSet.add(serviceOfferingDTO);
       Booking booking = bookingService.createBooking(bookingRequest, user, salon, serviceOfferingDTOSet);
        return  ResponseEntity.ok(booking);

    }


    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer()
              {
        List<Booking> bookings = bookingService.getBookingsByCustomer(1l);

        return ResponseEntity.ok(getBookingDTOs(bookings));

    }


    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport(
    ) throws Exception {

        SalonReport report = bookingService.getSalonReport(1L);
        return ResponseEntity.ok(report);

    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon ()  {

        List<Booking> bookings = bookingService.getBookingsBySalon(1L);
        return ResponseEntity.ok(getBookingDTOs(bookings));
    }



    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings) {

        return bookings.stream()
                .map(booking -> {
                    return BookingMapper.toDTO(booking);
                }).collect(Collectors.toSet());
    }


    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long bookingId) throws Exception {
        Booking booking = bookingService.getBookingById(bookingId);
//        Set<ServiceOfferingDTO> offeringDTOS=serviceOfferingService
//                .getServicesByIds(booking.getServiceIds()).getBody();
        BookingDTO bookingDTO=BookingMapper.toDTO(booking);

        return ResponseEntity.ok(bookingDTO);

    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus status) throws Exception {
        Booking updatedBooking = bookingService.updateBooking(bookingId, status);
//        Set<ServiceOfferingDTO> offeringDTOS=serviceOfferingService
//                .getServicesByIds(updatedBooking.getServiceIds()).getBody();
//
//        SalonDTO salonDTO;
//        try {
//            salonDTO=salonService.getSalonById(
//                    updatedBooking.getSalonId()
//            ).getBody();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        BookingDTO bookingDTO=BookingMapper.toDTO(updatedBooking);

        return new ResponseEntity<>(bookingDTO, HttpStatus.OK);

    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookedSlotsDTO>> getBookedSlots (
            @PathVariable Long salonId,
            @PathVariable LocalDate date
    ) throws Exception {

        List<Booking> bookings = bookingService.getBookingsByDate(date,salonId);

        List<BookedSlotsDTO> slotsDTOS = bookings.stream()
                .map(booking -> {
                    BookedSlotsDTO slotDto = new BookedSlotsDTO();
                    slotDto.setStartTime(booking.getStartTime());
                    slotDto.setEndTime(booking.getEndTime());
                    return slotDto;
                }).collect(Collectors.toList());



        return ResponseEntity.ok(slotsDTOS);


    }

}
