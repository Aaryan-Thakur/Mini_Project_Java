package com.example.demo;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PostRepsitory repo1;
	
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser()
	{
		User user = new User();
		user.setEmail("test2@gmail.com");
		user.setFirstname("Aaryan");
		user.setLastname("Thakur");
		user.setPassword("1234");
		
		User savedUser = repo.save(user);
		User existUser = entityManager.find(User.class,savedUser.getId());
		
		assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
		
	}

	@Test
	public void testFindUserByEmail() {
		String email="test4@gmail.com";
		
		User user = repo.findByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCreatePost()
	{
		Post post = new Post();
		post.setDescription("Test Post 1 Description");
		post.setTitle("Test Post 1 Title");
		post.setUsername("test1@gmail.com");
		
		post = repo1.save(post);
	}
	
	@Test
	public void testPostbyID() {
		long id=6;
		
		Post post = repo1.findByID(id);
		
		assertThat(post.id).isNotNull();
	}
}
