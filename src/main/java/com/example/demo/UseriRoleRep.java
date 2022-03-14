package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UseriRoleRep extends CrudRepository<UR, Long> {
	
	@Query("SELECT u FROM UR u WHERE u.user_id =?1 ")
	UR findByID(Long id);

}
