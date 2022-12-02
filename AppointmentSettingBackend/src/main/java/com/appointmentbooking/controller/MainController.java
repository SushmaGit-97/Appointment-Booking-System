package com.appointmentbooking.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appointmentbooking.entity.LocationDetails;
import com.appointmentbooking.entity.TimeSlot;
import com.appointmentbooking.entity.UserDetails;
import com.appointmentbooking.service.MainService;


@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/appointmentBooking")
public class MainController {
	
	private MainService mainService;

	
	@Autowired
	public MainController(MainService mainService) {
		this.mainService = mainService;
	}
	
	@PostMapping(value = "/registerUser")
	ResponseEntity<Map> saveUser(@RequestBody UserDetails userDetails){
		return ResponseEntity.status(HttpStatus.OK).body(mainService.registerUser(userDetails));
	}
	
	@PostMapping(value = "/loginUser")
	ResponseEntity<Map> loginUser(@RequestBody UserDetails userDetails){
		return ResponseEntity.status(HttpStatus.OK).body(mainService.loginUser(userDetails));
	}
	
	@PostMapping(value = "/bookAppointment")
	ResponseEntity<Map> bookSlot(@RequestBody TimeSlot timeSlot){
		return ResponseEntity.status(HttpStatus.OK).body(mainService.bookAppointment(timeSlot));
	}
	
	@PostMapping(value = "/addLocation")
	ResponseEntity<Map> addLocation(@RequestBody LocationDetails locationDetail){
		return ResponseEntity.status(HttpStatus.OK).body(mainService.addLocation(locationDetail));
	}
	
	
	@DeleteMapping(value = "/deleteLocation/{id}")
	ResponseEntity<Map> deleteLocation(@PathVariable(name = "id",required = true) Long id){
		return ResponseEntity.status(HttpStatus.OK).body(mainService.deleteLocationById(id));
	}
	
	@GetMapping(value = "/getUserAppointmentHistory/{id}")
	ResponseEntity<Map> getUserAppointmentHistory(@PathVariable(name = "id",required = true) Long id){
		return ResponseEntity.status(HttpStatus.OK).body(mainService.getUserAppointmentHistory(id));
	}
	
	@GetMapping(value = "/getAllBookingDetails")
	ResponseEntity<Map> getAllBookingDetails(){
		return ResponseEntity.status(HttpStatus.OK).body(mainService.getAllBookingDetails());
	}
	
	@DeleteMapping(value = "/deleteAppointment/{id}")
	ResponseEntity<Map> deleteAppointment(@PathVariable(name = "id",required = true) Long id){
		return ResponseEntity.status(HttpStatus.OK).body(mainService.deleteAppointmentById(id));
	}
	
	@PostMapping(value = "/availableTimeslot")
	ResponseEntity<Map> getAvailableTimeSlot(@RequestBody TimeSlot timeSlot){
		return ResponseEntity.status(HttpStatus.OK).body(mainService.getAvailableTimeslot(timeSlot));
	}
	
	@GetMapping(value = "/getLocationList")
	ResponseEntity<Map> getLocationList(){
		return ResponseEntity.status(HttpStatus.OK).body(mainService.getLocationList());
	}
	
}

