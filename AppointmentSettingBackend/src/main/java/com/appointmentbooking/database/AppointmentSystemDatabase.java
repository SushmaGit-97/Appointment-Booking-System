package com.appointmentbooking.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class AppointmentSystemDatabase {
	
	public static void main(String[] args) {
		try(Connection conn = createNewDBconnection()){
			AppointmentSystemDatabase(conn);
			System.out.println("Tables and schema created");
			conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	private static void AppointmentSystemDatabase(Connection connection) {
		try {
			connection.prepareStatement("DROP SCHEMA if exists AppointmentBookingApp").execute();
			PreparedStatement statement = connection.prepareStatement("CREATE DATABASE AppointmentBookingApp"); 
			statement.execute();
			connection.setSchema("AppointmentBookingApp");
			String createUser = "CREATE TABLE AppointmentBookingApp.user_details ("+ 
					"					id MEDIUMINT NOT NULL AUTO_INCREMENT,"+ 
					"					user_name varchar(150)," +
					"					email varchar(150)," +
					"					phone_number varchar(150)," +
					"					password varchar(150)," +
					"					address varchar(150)," +
					"					Constraint PK Primary Key(id)"+ 
					"					)";
			String createLocation = "CREATE TABLE AppointmentBookingApp.location (" + 
					"					id MEDIUMINT NOT NULL AUTO_INCREMENT,"+
					"					name varchar(150)," +
					"					weekday_start_time MEDIUMINT," +
					"					weekday_end_time MEDIUMINT," +
					"					weekend_start_time MEDIUMINT," +
					"					weekend_end_time MEDIUMINT," +
					"					Constraint PK Primary Key(id)"+ 
					")";
			String createService = "CREATE TABLE AppointmentBookingApp.service_details (" + 
					"					id MEDIUMINT NOT NULL AUTO_INCREMENT,"+
					"					location_id MEDIUMINT NOT NULL,"+
					"					name varchar(150)," +
					"					Constraint PK Primary Key(id),"+
					" 	 				Constraint location_service_fk FOREIGN KEY (location_id) REFERENCES location(id)"+
					")";
			String createTimeSlot = "CREATE TABLE AppointmentBookingApp.appointment (" +
					"					id MEDIUMINT NOT NULL AUTO_INCREMENT,"+
					"					location_id MEDIUMINT NOT NULL,"+
					"					service_id MEDIUMINT NOT NULL,"+
					"					user_id MEDIUMINT NOT NULL,"+
					"					reason varchar(150)," +
					"					booking_user_name varchar(150)," +
					"					booking_phone_number varchar(150)," +
					"					booking_email varchar(150)," +
					"					booking_address varchar(450)," +
					"					booking_date datetime," +
					"					start_time MEDIUMINT," +
					"					end_time MEDIUMINT," +
					"					Constraint PK Primary Key(id),"+ 
					" 	 				Constraint user_time_fk FOREIGN KEY (user_id) REFERENCES user_details(id)"+
					")";
			
			
			connection.prepareStatement(createUser).execute();
			connection.prepareStatement(createLocation).execute();
			connection.prepareStatement(createService).execute();
			connection.prepareStatement(createTimeSlot).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static String dbhost = "jdbc:mysql://localhost:3306";
	private static String username = "root";
	private static String password = "root";
	private static Connection conn;
	
	@SuppressWarnings("finally")
	public static Connection createNewDBconnection() {
		try  {	
			conn = DriverManager.getConnection(
					dbhost, username, password);	
		} catch (SQLException e) {
			System.out.println("Cannot create database connection");
			e.printStackTrace();
		} finally {
			return conn;	
		}		
	}
}
