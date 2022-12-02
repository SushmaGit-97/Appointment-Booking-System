package com.appointmentbooking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.appointmentbooking.entity.Location;



@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
	
}
