package com.appointmentbooking.service;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.appointmentbooking.entity.Location;
import com.appointmentbooking.entity.LocationDetails;
import com.appointmentbooking.entity.ServiceDetails;
import com.appointmentbooking.entity.TimeSlot;
import com.appointmentbooking.entity.TimeslotDetails;
import com.appointmentbooking.entity.UserDetails;
import com.appointmentbooking.repository.LocationRepository;
import com.appointmentbooking.repository.ServiceDetailsRepository;
import com.appointmentbooking.repository.TimeSlotRepository;
import com.appointmentbooking.repository.UserDetailsRepository;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.parameter.ParticipationLevel;
import biweekly.property.Attendee;
import biweekly.property.Method;
import biweekly.property.Organizer;
import biweekly.util.Duration;



@Service
public class MainServiceImpl implements MainService {

	@Autowired
	UserDetailsRepository userDetailsRepository;

	@Autowired
	TimeSlotRepository timeSlotRepository;

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	ServiceDetailsRepository serviceDetailsRepository;
	
	

	@Autowired
	public MainServiceImpl(UserDetailsRepository userDetailsRepository,TimeSlotRepository timeSlotRepository,
			LocationRepository locationRepository,ServiceDetailsRepository serviceDetailsRepository) {
		this.userDetailsRepository = userDetailsRepository;
		this.timeSlotRepository = timeSlotRepository;
		this.locationRepository = locationRepository;
		this.serviceDetailsRepository = serviceDetailsRepository;
	}

	@Override
	public Map registerUser(UserDetails user) {
		Map<String, Object> resultMap = new HashMap<>();
		Optional<UserDetails> userOpt = userDetailsRepository.findByUserName(user.getUserName());
		if(userOpt.isPresent()) {
			resultMap.put("isSuccess", false);
			resultMap.put("error", "username already exists");
			return resultMap;
		}
		user = userDetailsRepository.save(user);
		resultMap.put("isSuccess", true);
		resultMap.put("user", user);
		return resultMap;
	}

	@Override
	public Map<String, Object> loginUser(UserDetails user) {
		Map<String, Object> resultMap = new HashMap<>();
		Optional<UserDetails> userOpt = userDetailsRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword());
		if(userOpt.isPresent()) {
			resultMap.put("user", userOpt.get());
			resultMap.put("isValidUser", true);
		}
		else {
			resultMap.put("isValidUser", false);
		}
			
		return resultMap;
	}

	@Override
	public Map addLocation(LocationDetails locationDetail) {
		Map<String, Object> resultMap = new HashMap<>();
		List<ServiceDetails> newServiceList = new ArrayList<>();
		List<Long> deleteServiceIdList = new ArrayList<>();
		Location location = locationDetail.getLocation();
		location = locationRepository.save(location);
		locationDetail.setLocation(location);
		Long locationId = location.getId();
		List<ServiceDetails> existingServices = serviceDetailsRepository.findAllByLocationId(location.getId());
		List<ServiceDetails> updatedServices = locationDetail.getServices();
		
		List<Long> updatedServiceIdList = updatedServices.stream()
				.filter(service -> service.getId() != null && service.getId() != 0l)
				.map(ServiceDetails::getId).collect(Collectors.toList());
		
		updatedServices.forEach(service -> {
			if(service.getId() == null || service.getId() == 0l) {
				ServiceDetails newService = new ServiceDetails();
				newService.setLocationId(locationId);
				newService.setName(service.getName());
				newServiceList.add(newService);
			}
		});
		existingServices.forEach(service ->{
			if(!updatedServiceIdList.contains(service.getId())){
				deleteServiceIdList.add(service.getId());
			}
		});
		if(!CollectionUtils.isEmpty(newServiceList)) {
			serviceDetailsRepository.saveAll(newServiceList);
		}
		if(!CollectionUtils.isEmpty(deleteServiceIdList)) {
			serviceDetailsRepository.deleteAllById(deleteServiceIdList);
		}
		existingServices = serviceDetailsRepository.findAllByLocationId(location.getId());
		locationDetail.setServices(existingServices);
		resultMap.put("locationDetail", locationDetail);
		return resultMap;
	}
	
	@Override
	@Transactional
	public Map<String, Object> deleteLocationById(Long id) {
		Map<String, Object> resultMap = new HashMap<>();
		Optional<Location> locationOpt = locationRepository.findById(id);
		if(locationOpt.isPresent()) {
			timeSlotRepository.deleteAllByLocationId(id);
			serviceDetailsRepository.deleteAllByLocationId(id);
			locationRepository.delete(locationOpt.get());
		}
		return resultMap;
	}

	
	@Override
	public Map<String, Object> getUserAppointmentHistory(Long id) {
		Map<String, Object> resultMap = new HashMap<>();
		List<TimeSlot> timeslots = timeSlotRepository.findByUserId(id);
		Map<Long,Location> locationMap = getLocationMap(timeslots);
		List<TimeslotDetails> timeslotDetails = getTimeslotDetails(timeslots, locationMap);
		resultMap.put("userAppointmentHistory",timeslotDetails);
		return resultMap;
	}

	private List<TimeslotDetails> getTimeslotDetails(List<TimeSlot> timeslots, Map<Long, Location> locationMap) {
		List<TimeslotDetails> timeslotDetails = new ArrayList<>();
		Map<Long,ServiceDetails> serviceMap = getServiceMap(timeslots);
		timeslots.forEach(timeslot -> {
			Location location = locationMap.get(timeslot.getLocationId());
			ServiceDetails service = serviceMap.get(timeslot.getServiceId());
			TimeslotDetails timeslotDetail = new TimeslotDetails();
			timeslotDetail.setBookingAddress(timeslot.getBookingAddress());
			timeslotDetail.setBookingDate(timeslot.getBookingDate());
			timeslotDetail.setBookingEmail(timeslot.getBookingEmail());
			timeslotDetail.setBookingPhoneNumber(timeslot.getBookingPhoneNumber());
			timeslotDetail.setBookingUserName(timeslot.getBookingUserName());
			timeslotDetail.setEndTime(timeslot.getEndTime());
			timeslotDetail.setId(timeslot.getId());
			timeslotDetail.setLocationId(timeslot.getLocationId());
			timeslotDetail.setLocationName(location.getName());
			timeslotDetail.setReason(timeslot.getReason());
			timeslotDetail.setServiceId(timeslot.getServiceId());
			timeslotDetail.setStartTime(timeslot.getStartTime());
			timeslotDetail.setUserId(timeslot.getUserId());
			timeslotDetail.setServiceName(service.getName());
			timeslotDetails.add(timeslotDetail);
		});
		return timeslotDetails;
	}
	
	private Map<Long,ServiceDetails> getServiceMap(List<TimeSlot> timeslots) {
		List<Long> serviceIds = timeslots.stream().map(TimeSlot::getServiceId).collect(Collectors.toList());
		List<ServiceDetails> services = (List<ServiceDetails>) serviceDetailsRepository.findAllById(serviceIds);
		Map<Long,ServiceDetails> serviceMap = services.stream()
				.collect(Collectors.toMap(ServiceDetails::getId,service -> service));
		return serviceMap;
	}
	

	private Map<Long,Location> getLocationMap(List<TimeSlot> timeslots) {
		List<Long> locationIds = timeslots.stream().map(TimeSlot::getLocationId).collect(Collectors.toList());
		List<Location> locations = (List<Location>) locationRepository.findAllById(locationIds);
		Map<Long,Location> locationMap = locations.stream().collect(Collectors.toMap(Location::getId,location -> location));
		return locationMap;
	}

	@Override
	public Map<String, Object> getAllBookingDetails() {
		Map<String, Object> resultMap = new HashMap<>();
		List<TimeSlot> timeslots = (List<TimeSlot>) timeSlotRepository.findAll();
		Map<Long,Location> locationMap = getLocationMap(timeslots);
		List<TimeslotDetails> timeslotDetails = getTimeslotDetails(timeslots, locationMap);
		resultMap.put("history",timeslotDetails);
		return resultMap;
	}
	
	@Override
	@Transactional
	public Map<String, Object> deleteAppointmentById(Long id){
		Map<String, Object> resultMap = new HashMap<>();
		if(timeSlotRepository.findById(id).isPresent()) {
			timeSlotRepository.deleteById(id);
			resultMap.put("sucess",true);
		}else {
			resultMap.put("sucess",false);
		}
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getAvailableTimeslot(TimeSlot timeSlot) {
		Map<String, Object> resultMap = new HashMap<>();
		List<TimeSlot> timeSlots = timeSlotRepository.findByLocationIdAndServiceIdAndBookingDateOrderByStartTime(timeSlot.getLocationId(),
				timeSlot.getServiceId(),timeSlot.getBookingDate());
		List<Integer> bookedSlots = timeSlots.stream().map(TimeSlot::getStartTime).collect(Collectors.toList());
		List<Integer> timeSlotList = new ArrayList<>();
		List<Integer> availableTimeSlots = new ArrayList<>();
		Date date = timeSlot.getBookingDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		Optional<Location> locationOpt = locationRepository.findById(timeSlot.getLocationId());
		if(!locationOpt.isPresent()) {
			resultMap.put("error", "Location not found");
			return resultMap;
		}
		Location location = locationOpt.get();
		int locationStartTime = location.getWeekendStartTime();
		int locationEndTime = location.getWeekendEndTime();
		if(day != 1 && day !=7) {
			locationStartTime = location.getWeekdayStartTime();
			locationEndTime = location.getWeekdayEndTime();
		}
		for(int i = locationStartTime; i < locationEndTime; i++) {
			timeSlotList.add(i);
		}
		timeSlotList.forEach(timeslot ->{
			if(!bookedSlots.contains(timeslot))
				availableTimeSlots.add(timeslot);
		});
		resultMap.put("availableSlot", availableTimeSlots);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> bookAppointment(TimeSlot timeSlot){
		Map<String, Object> resultMap = new HashMap<>();
		Date date = timeSlot.getBookingDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		int startTime = timeSlot.getStartTime();
		Optional<Location> locationOpt = locationRepository.findById(timeSlot.getLocationId());
		if(!locationOpt.isPresent()) {
			resultMap.put("error", "Location not found");
			return resultMap;
		}
		Location location = locationOpt.get();
		int locationStartTime = location.getWeekendStartTime();
		int locationEndTime = location.getWeekendEndTime();

		if(day != 1 && day !=7) {
			locationStartTime = location.getWeekdayStartTime();
			locationEndTime = location.getWeekdayEndTime();
		}
		int bookingTime = timeSlot.getStartTime();
//		List<TimeSlot> timeSlots = timeSlotRepository.findByLocationIdAndServiceIdAndBookingDateOrderByStartTime(timeSlot.getLocationId(),timeSlot.getServiceId(),date);
		Optional<UserDetails> userOptional = userDetailsRepository.findById(timeSlot.getUserId());
		UserDetails user=userOptional.get();
		timeSlot.setBookingPhoneNumber(user.getPhoneNumber());
		timeSlot.setBookingEmail(user.getEmail());
		timeSlot.setBookingUserName(user.getUserName());
		timeSlot.setBookingAddress(user.getAddress());
		
		timeSlot = timeSlotRepository.save(timeSlot);
		resultMap.put("appointmentDetails", timeSlot);
		
		sendMail(timeSlot);

		return resultMap;
	}

	private void sendMail(TimeSlot timeSlot) {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", true);
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.ssl.trust", "*");
	     
	     prop.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		
		String username = "sushmab958@gmail.com";
		String password = "adaxwbxttkdrqcxb";
		
		SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMMM yyyy");  
		String strDate = formatter.format(timeSlot.getBookingDate());
		SimpleDateFormat formatter2 = new SimpleDateFormat("z");  
		String amOrpm = timeSlot.getStartTime() < 12 ? "AM" : "PM";
		int time = timeSlot.getStartTime() > 12 ? timeSlot.getStartTime() - 12 : timeSlot.getStartTime();
		String bookingTime = strDate +", " + time + ":00 " + amOrpm + " ";
		String address = StringUtils.hasText(timeSlot.getBookingAddress()) ? "" : timeSlot.getBookingAddress();
		
		Session session = Session.getInstance(prop, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(username, password);
		    }
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(
			  Message.RecipientType.TO, InternetAddress.parse(timeSlot.getBookingEmail()));
			message.setSubject("Notification: Appointment Booking System");
	
			String msg = "Hi "+ timeSlot.getBookingUserName()+",\n\n"+"Your appointment has been confirmed!\n\n"
					
					+  bookingTime;
				
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setText(msg);
			
			Multipart multipart = new MimeMultipart("mixed");
			multipart.addBodyPart(mimeBodyPart);
//			multipart.addBodyPart(iCalPart);
			message.setContent(multipart);
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}		
	}
	
	
	
	@Override
	public Map<String, Object> getLocationList(){
		Map<String, Object> resultMap = new HashMap<>();
		List<LocationDetails> locationDetails = new ArrayList<>();
		List<Location> locations = (List<Location>) locationRepository.findAll();
		List<ServiceDetails> services = (List<ServiceDetails>) serviceDetailsRepository.findAll();
		Map<Long,List<ServiceDetails>> serviceMap = services.stream().collect(Collectors.groupingBy(ServiceDetails::getLocationId));
		locations.stream().forEach(location -> {
			List<ServiceDetails> serviceList = serviceMap.get(location.getId());
			LocationDetails locationDetail = new LocationDetails();
			locationDetail.setLocation(location);
			locationDetail.setServices(serviceList);
			locationDetails.add(locationDetail);
		});
		resultMap.put("locationList", locationDetails);
		return resultMap;
	}


}
