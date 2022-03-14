package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PostRepsitory extends CrudRepository<Post, Long> {
	
	@Query("SELECT u FROM Post u WHERE u.id =?1 ")
	Post findByID(Long id);

}
