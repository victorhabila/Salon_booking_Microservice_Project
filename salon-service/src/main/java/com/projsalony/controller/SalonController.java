package com.projsalony.controller;

import com.projsalony.mapper.SalonMapper;
import com.projsalony.model.Salon;
import com.projsalony.payload.dto.SalonDTO;
import com.projsalony.payload.dto.UserDTO;
import com.projsalony.service.SalonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
public class SalonController {

    private final SalonService salonService;

    public SalonController(SalonService salonService) {
        this.salonService = salonService;
    }

    @PostMapping("/create")
    public ResponseEntity<SalonDTO> createSalon(@RequestBody SalonDTO salonDTO){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L); // let do this for now. We will update it later with keycloak to grab jwt token and our user
        Salon salon = salonService.createSalon(salonDTO, userDTO);
        SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);

        return ResponseEntity.ok(salonDTO1);

    }

    @PatchMapping("/{salonId}")
    public ResponseEntity<SalonDTO> updateSalon(@RequestBody SalonDTO salonDTO, @PathVariable Long salonId) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L); // let do this for now. We will update it later with keycloak to grab jwt token and our user
        Salon createdSalon = salonService.updateSalon(salonDTO, userDTO, salonId);
        SalonDTO salonDTO1 = SalonMapper.mapToDTO(createdSalon);
        return ResponseEntity.ok(salonDTO1);

    }
    @GetMapping
    public ResponseEntity<List<SalonDTO>> getSalons() throws Exception {
        List<Salon> salons = salonService.getAllSalons();

        List<SalonDTO>salonDTOS = salons.stream().map((salon)->
        {
            SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
            return salonDTO;
        }).toList();
       return ResponseEntity.ok(salonDTOS);
    }

    @GetMapping("/{salonId}")
    public ResponseEntity<SalonDTO> getSalonById(@PathVariable Long salonId) throws Exception {
       Salon salon = salonService.getSalonById(salonId);
       SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
       return ResponseEntity.ok(salonDTO);

    }

    @GetMapping("/search")
    public ResponseEntity<List<SalonDTO>> searchSalons(@RequestParam("city") String city) throws Exception {
        List<Salon> salons = salonService.searchSalonByCity(city);

        List<SalonDTO>salonDTOS = salons.stream().map((salon)->
        {
            SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
            return salonDTO;
        }).toList();
        return ResponseEntity.ok(salonDTOS);
    }

//    @GetMapping("/{ownerId}")
//    public ResponseEntity<SalonDTO> getSalonByOwnerId(@PathVariable Long salonId) throws Exception {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(1L); // let do this for now. We will update it later with keycloak to grab jwt token and our user
//        Salon salon = salonService.getSalonByOwnerId(userDTO.getId());
//
//        SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
//        return ResponseEntity.ok(salonDTO);
//
//    }

}
