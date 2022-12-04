package com.appointmentbooking.controller;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.appointmentbooking.entity.LocationDetails;
import com.appointmentbooking.entity.TimeSlot;
import com.appointmentbooking.entity.UserDetails;
import com.appointmentbooking.service.AppointmentService;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/appointmentBooking")
public class AppointmentController {
	private AppointmentService appointmentService;
	@Autowired
	public AppointmentController(AppointmentService appointmentService)
	{
		this.appointmentService = appointmentService;
	}
	
	//User Login Mapping with front end
	
	@PostMapping(value = "/Userlogin")
	ResponseEntity<Map> userAuthentication(@RequestBody UserDetails userDetails){
		return ResponseEntity.status(HttpStatus.OK).body(appointmentService.Userlogin(userDetails));
	}
	
	//User Registration Mapping with front end
	
	@PostMapping(value = "/UserRegistration")
	ResponseEntity<Map> createNewUserAccount(@RequestBody UserDetails userDetails){
		return ResponseEntity.status(HttpStatus.OK).body(appointmentService.UserRegistration(userDetails));
	}
	
// Appointment booking mapping with front end

	@PostMapping(value = "/AppointmentBooking")
	ResponseEntity<Map> bookSlot(@RequestBody TimeSlot timeSlot){
		return ResponseEntity.status(HttpStatus.OK).body(appointmentService.AppointmentBooking(timeSlot));
	}
	
	//Adding new locations
	@PostMapping(value = "/addLocation")
	ResponseEntity<Map> addLocation(@RequestBody LocationDetails locationDetail){
		return ResponseEntity.status(HttpStatus.OK).body(appointmentService.addLocation(locationDetail));
	}
	
	// Get booking details 
	
	@GetMapping(value = "/getBookingDetails")
	ResponseEntity<Map> getBookingDetails(){
		return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getBookingDetails());
	}
	
	//Getting available time slots
	@PostMapping(value = "/AvailableTimeslot")
	ResponseEntity<Map> getAvailableTimeSlot(@RequestBody TimeSlot timeSlot){
		return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getAvailableTimeslot(timeSlot));
	}
	
	//Getting available location list
	@GetMapping(value = "/getLocationList")
	ResponseEntity<Map> getLocationList(){
		return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getLocationList());
	}
}

	
	
	
	

