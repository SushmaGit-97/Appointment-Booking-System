package com.appointmentbooking.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.appointmentbooking.entity.UserDetails;


@Repository
public interface UserDetailsRepository extends CrudRepository<UserDetails, Long> {
	
	Optional<UserDetails> findByUserNameAndPassword(String username,String password);
	
	Optional<UserDetails> findByUserName(String username);

}
