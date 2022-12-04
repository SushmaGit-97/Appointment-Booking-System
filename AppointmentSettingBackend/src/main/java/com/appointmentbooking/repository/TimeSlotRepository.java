package com.appointmentbooking.repository;
import java.sql.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.appointmentbooking.entity.TimeSlot;



@Repository
public interface TimeSlotRepository extends CrudRepository<TimeSlot, Long> {
	
	List<TimeSlot> findByLocationIdAndServiceIdAndBookingDateOrderByStartTime(Long locationId, Long serviceId, Date bookingDate);
	
	List<TimeSlot> findByUserId(Long userId);
	
	List<TimeSlot> findByLocationId(Long locationId);
	
	void deleteAllByLocationId(Long locationId);
	
}



