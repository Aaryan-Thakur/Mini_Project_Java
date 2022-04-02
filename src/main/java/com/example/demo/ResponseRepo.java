package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ResponseRepo extends CrudRepository<Response, Long> {
	
	@Query("SELECT u FROM Response u WHERE u.post_id =?1 ")
	Iterable<Response> findByID(Post post_id);

    // @Query("SELECT u FROM Response u WHERE u.Responder =?1 ")
	// Response findByName(String responder);

    // @Query("SELECT u FROM Post u WHERE u.id =?1 ")
	// Post findByID(Long id);

	// @Query("SELECT u FROM Post u WHERE u.OP_id =?1 ")
	// Iterable<Post> findByOPID(Long id);

}
