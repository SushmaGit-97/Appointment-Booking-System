package com.appointmentbooking.entity;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "user_details")
@Entity
@Data
public class UserDetails {
	
	@Id
	@GeneratedValue
	private Long id;
	private String userName;
	private String email;
	private String phoneNumber;
	private String password;
	private String address;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public UserDetails registerUser(UserDetails userDetails) {
		// TODO Auto-generated method stub
		return null;
	}
	//
	public UserDetails UserRegistration(UserDetails userDetails) {
		// TODO Auto-generated method stub
		return null;
	}
	public UserDetails Userlogin(UserDetails userDetails) {
		// TODO Auto-generated method stub
		return null;
	}
	public int getWeekdayInitialTime() {
		// TODO Auto-generated method stub
		return 0;
	}
	public static Optional<UserDetails> findById(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
