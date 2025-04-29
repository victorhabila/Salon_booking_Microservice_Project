package com.projsalony.service.Impl;

import com.projsalony.model.Salon;
import com.projsalony.payload.dto.SalonDTO;
import com.projsalony.payload.dto.UserDTO;
import com.projsalony.repository.SalonRepository;
import com.projsalony.service.SalonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalonServiceImpl implements SalonService {

    private final SalonRepository salonRepository;

    public SalonServiceImpl(SalonRepository salonRepository) {
        this.salonRepository = salonRepository;
    }

    @Override
    public Salon createSalon(SalonDTO req, UserDTO user) {

        Salon salon = new Salon();
        salon.setName(req.getName());
        salon.setAddress(req.getAddress());
        salon.setEmail(req.getEmail());
        salon.setCity(req.getCity());
        salon.setImages(req.getImages());
        salon.setOwnerId(user.getId());
        salon.setOpenTime(req.getOpenTime());
        salon.setCloseTime(req.getCloseTime());
        salon.setPhoneNumber(req.getPhoneNumber());
        return salonRepository.save(salon);
    }

    @Override
    public Salon updateSalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception {

        Salon existingSalon = salonRepository.findById(salonId).orElse(null);

        if(existingSalon != null && salon.getOwnerId().equals(user.getId())){ // we want to make sure only owner of the salon can update their salon dn no one else
            existingSalon.setName(salon.getName());
            existingSalon.setAddress(salon.getAddress());
            existingSalon.setEmail(salon.getEmail());
            existingSalon.setCity(salon.getCity());
            existingSalon.setImages(salon.getImages());
            existingSalon.setOwnerId(user.getId());
            existingSalon.setOpenTime(salon.getOpenTime());
            existingSalon.setCloseTime(salon.getCloseTime());
            existingSalon.setPhoneNumber(salon.getPhoneNumber());

            salonRepository.save(existingSalon);
        }
        throw new Exception("salon not exist");
    }

    @Override
    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) throws Exception {
        Salon salon=  salonRepository.findById(salonId).orElse(null);

        if(salon == null){
            throw new Exception("salon not exist");
        }
        return salon;
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {

        return salonRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Salon> searchSalonByCity(String city) {
        return salonRepository.searchSalons(city);
    }
}
