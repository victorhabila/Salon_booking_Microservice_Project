package com.projsalony.service;

import com.projsalony.model.Salon;
import com.projsalony.payload.dto.SalonDTO;
import com.projsalony.payload.dto.UserDTO;

import java.util.List;

public interface SalonService {

    Salon createSalon(SalonDTO salon, UserDTO user); // PROVIDING THE USER DATA THAT OWNS THE SALON

    Salon updateSalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception; // added userDTO to compare if the user id in the salon db is same as the user id. if yes it update else throws error

    List<Salon> getAllSalons();

    Salon getSalonById(Long salonId) throws Exception;

    Salon getSalonByOwnerId(Long ownerId);

    List<Salon> searchSalonByCity(String city);



}
