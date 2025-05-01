package com.projsalony.service;

import com.projsalony.dto.CategoryDTO;
import com.projsalony.dto.SalonDTO;
import com.projsalony.dto.ServiceDTO;
import com.projsalony.model.ServiceOffering;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO);

    ServiceOffering updateService(ServiceOffering serviceOffering, Long serviceId) throws Exception;

    Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId); // added category id to enable use to filter service also by category

    Set<ServiceOffering> getServicesByIds(Set<Long> ids);// user provides set of ids  and by that we provide list of services

    ServiceOffering getServiceById(Long id) throws Exception;
}
