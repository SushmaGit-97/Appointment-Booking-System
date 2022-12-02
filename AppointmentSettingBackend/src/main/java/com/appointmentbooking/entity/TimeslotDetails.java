package com.appointmentbooking.entity;


import java.sql.Date;



import lombok.Data;

@Data
public class TimeslotDetails {
	
	private Long id;
	private Long locationId;
	private Long serviceId;
	private Long userId;
	private String reason;
	private String bookingUserName;
	private String bookingPhoneNumber;
	private String bookingEmail;
	private String bookingAddress;
	private Date bookingDate;
	private int startTime;
	private int endTime;
	private String locationName;
	private String serviceName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getBookingUserName() {
		return bookingUserName;
	}
	public void setBookingUserName(String bookingUserName) {
		this.bookingUserName = bookingUserName;
	}
	public String getBookingPhoneNumber() {
		return bookingPhoneNumber;
	}
	public void setBookingPhoneNumber(String bookingPhoneNumber) {
		this.bookingPhoneNumber = bookingPhoneNumber;
	}
	public String getBookingEmail() {
		return bookingEmail;
	}
	public void setBookingEmail(String bookingEmail) {
		this.bookingEmail = bookingEmail;
	}
	public String getBookingAddress() {
		return bookingAddress;
	}
	public void setBookingAddress(String bookingAddress) {
		this.bookingAddress = bookingAddress;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
}
