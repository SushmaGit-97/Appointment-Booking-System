package com.appointmentbooking.service;

import java.util.Map;

import com.appointmentbooking.entity.LocationDetails;
import com.appointmentbooking.entity.TimeSlot;
import com.appointmentbooking.entity.UserDetails;


public interface MainService {


	Map<String, Object> registerUser(UserDetails user);

	Map<String, Object> loginUser(UserDetails user);
	
	Map<String, Object> addLocation(LocationDetails locationDetail);

	Map<String, Object> deleteLocationById(Long id);
	
	Map<String, Object> getUserAppointmentHistory(Long userId);

	Map<String, Object> getAllBookingDetails();

	Map<String, Object> deleteAppointmentById(Long id);

	Map<String, Object> getAvailableTimeslot(TimeSlot timeSlot);
	
	Map<String, Object> bookAppointment(TimeSlot timeSlot);
	
	public Map<String, Object> getLocationList();

}
