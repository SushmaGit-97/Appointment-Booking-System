package com.booking.application.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.BeforeMethod;

import com.hospitalappointmentbooking.entity.Location;
import com.hospitalappointmentbooking.entity.LocationDetails;
import com.hospitalappointmentbooking.entity.ServiceDetails;
import com.hospitalappointmentbooking.repository.LocationRepository;
import com.hospitalappointmentbooking.repository.ServiceDetailsRepository;
import com.hospitalappointmentbooking.repository.TimeSlotRepository;
import com.hospitalappointmentbooking.repository.UserDetailsRepository;
import com.hospitalappointmentbooking.service.MainServiceImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=LocationServiceImplTest.class)
public class LocationServiceImplTest {
	
	
	@Mock
	LocationRepository locationRepository;
	
	@Mock
	ServiceDetailsRepository serviceRepository;
	
	@Mock
	TimeSlotRepository timeSlotRepository;
	
	@Mock
	UserDetailsRepository userDetailsRepository;
	
	@InjectMocks
	@Spy
	MainServiceImpl classUnderTest = new MainServiceImpl(userDetailsRepository, timeSlotRepository, locationRepository, serviceRepository);
	
	
	@BeforeMethod
	void setUp() throws Exception{
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testsaveLocation() {
		List<Location> locations = new ArrayList<>();
		Location location = new Location();
		locations.add(location);
		List<ServiceDetails> existingServices = new ArrayList<>();
		ServiceDetails service = new ServiceDetails();
		existingServices.add(service);
		LocationDetails locationDetails = new LocationDetails();
		locationDetails.setServices(existingServices);
		locationDetails.setLocation(location);
		when(locationRepository.save(Mockito.any())).thenReturn(location);
		when(serviceRepository.findAllByLocationId(Mockito.any())).thenReturn(existingServices);
		when(serviceRepository.saveAll(Mockito.anyList())).thenReturn(existingServices);
		doNothing().when(serviceRepository).deleteAllById(Mockito.anyList());
		Map<String, Object> map = classUnderTest.addLocation(locationDetails);
		assertEquals(1, map.size());
	}
	
	@Test
	public void testgetAllLocations() {
		List<Location> locations = new ArrayList<>();
		Location location = new Location();
		locations.add(location);
		List<ServiceDetails> existingServices = new ArrayList<>();
		ServiceDetails service = new ServiceDetails();
		service.setLocationId(1l);
		existingServices.add(service);
		when(locationRepository.findAll()).thenReturn(locations);
		when(serviceRepository.findAll()).thenReturn(existingServices);
		
		Map<String, Object> map = classUnderTest.getLocationList();
		
		assertEquals(1, map.size());
	}
	
}
