package com.appointmentbooking.entity;


import java.util.List;

import lombok.Data;

@Data
public class LocationDetails {

	private Location location;
	private List<ServiceDetails> services;
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public List<ServiceDetails> getServices() {
		return services;
	}
	public void setServices(List<ServiceDetails> services) {
		this.services = services;
	}
	
}
