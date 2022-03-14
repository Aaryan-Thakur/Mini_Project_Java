package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RequestRepo extends CrudRepository<Request, Long> {

	@Query("SELECT u FROM Request u WHERE u.id =?1 ")
	Request findByID(Long id);
	

	@Query("DELETE FROM Request u WHERE u.id=:id")
	Request deleteBooks(@Param("id") Long id);
}
