package com.appointmentbooking.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
@Table(name = "location")
@Entity
@Data
public class Location {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private int weekdayStartTime;
	private int weekdayEndTime;
	private int weekendStartTime;
	private int weekendEndTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWeekdayStartTime() {
		return weekdayStartTime;
	}
	public void setWeekdayStartTime(int weekdayStartTime) {
		this.weekdayStartTime = weekdayStartTime;
	}
	public int getWeekdayEndTime() {
		return weekdayEndTime;
	}
	public void setWeekdayEndTime(int weekdayEndTime) {
		this.weekdayEndTime = weekdayEndTime;
	}
	public int getWeekendStartTime() {
		return weekendStartTime;
	}
	public void setWeekendStartTime(int weekendStartTime) {
		this.weekendStartTime = weekendStartTime;
	}
	public int getWeekendEndTime() {
		return weekendEndTime;
	}
	public void setWeekendEndTime(int weekendEndTime) {
		this.weekendEndTime = weekendEndTime;
	}	
}
