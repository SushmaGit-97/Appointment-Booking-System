package com.appointmentbooking.service;

import java.util.Map;

import com.appointmentbooking.entity.LocationDetails;
import com.appointmentbooking.entity.TimeSlot;
import com.appointmentbooking.entity.UserDetails;


public interface AppointmentService {
	
	Map<String, Object> Userlogin(UserDetails userDetails);

	Map<String, Object> UserRegistration(UserDetails userDetails);
	
	Map<String, Object> AppointmentBooking(TimeSlot timeSlot);
	
	Map<String, Object> addLocation(LocationDetails locationDetail);
	
	Map<String, Object> getBookingDetails();
	
	Map<String, Object> getAvailableTimeslot(TimeSlot timeSlot);
	
	Map<String, Object> getLocationList();
}
	
	
	


