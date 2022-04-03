package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepo extends CrudRepository<Comment, Long> {
	
	@Query("SELECT u FROM Comment u WHERE u.post_id =?1 ")
	Iterable<Comment> findByID(Post post_id);

}
