package com.appointmentbooking.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.appointmentbooking.entity.ServiceDetails;


@Repository
public interface ServiceDetailsRepository extends CrudRepository<ServiceDetails, Long> {
	
	List<ServiceDetails> findAllByLocationId(Long locationId);
	
	void deleteAllByLocationId(Long locationId);
}
