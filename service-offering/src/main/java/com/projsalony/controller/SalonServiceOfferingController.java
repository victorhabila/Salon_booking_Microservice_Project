package com.projsalony.controller;

import com.projsalony.dto.CategoryDTO;
import com.projsalony.dto.SalonDTO;
import com.projsalony.dto.ServiceDTO;
import com.projsalony.model.ServiceOffering;
import com.projsalony.service.ServiceOfferingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    public SalonServiceOfferingController(ServiceOfferingService serviceOfferingService) {
        this.serviceOfferingService = serviceOfferingService;
    }

    @PostMapping()
    public ResponseEntity<ServiceOffering> createService(@RequestBody ServiceDTO serviceDTO){
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1l);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(serviceDTO.getCategory());

        ServiceOffering serviceOfferings = serviceOfferingService.createService(salonDTO,serviceDTO,categoryDTO);

        return ResponseEntity.ok(serviceOfferings);

    }

    @PostMapping("/{id}")
    public ResponseEntity<ServiceOffering> updateService(@PathVariable Long id, @RequestBody ServiceOffering serviceOffering) throws Exception {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1l);
        CategoryDTO categoryDTO = new CategoryDTO(); //static category DTO
        categoryDTO.setId(1L);
        ServiceOffering serviceOfferings = serviceOfferingService.updateService(serviceOffering, id);
        return ResponseEntity.ok(serviceOfferings);

    }

}
