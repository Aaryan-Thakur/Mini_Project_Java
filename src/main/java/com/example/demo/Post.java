package com.example.demo;

import javax.persistence.Column;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {

	
///////////////////////////////////////////////////////////////////
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
/////////////////////////////////////////////////////////////////////

	@Column(nullable = true)	
	public Long OP_id;

	public Long getOP_id() {
		return OP_id;
	}
	public void setOP_id(Long oP_id) {
		OP_id = oP_id;
	}
	
///////////////////////////////////////////////////////////////////

	@Column(nullable = true,length=100)	
	public String Title;

	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}

///////////////////////////////////////////////////////////////////

	@Column(nullable = true,length=500)	
	public String Description;

	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}

////////////////////////////////////////////////////////////////////
	
	@Column(nullable = true,unique=false,length=45)	
	public String username;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

/////////////////////////////////////////////////////////////////////

	@Column(nullable = true,length=500)	
	public String Respone;

	public String getRespone() {
		return Respone;
	}
	public void setRespone(String respone) {
		Respone = respone;
	}

//////////////////////////////////////////////////////////////////////

	@Column(nullable = true,length=10)	
	public LocalDate Create_Date;

	public LocalDate getCreate_Date() {
		return Create_Date;
	}
	public void setCreate_Date(LocalDate create_Date) {
		Create_Date = create_Date;
	}

//////////////////////////////////////////////////////////////////////

	@Column(nullable = true,length=100)	
	public String Email;

	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}

//////////////////////////////////////////////////////////////////////


	@Column(nullable = true,unique=false,length=45)	
	public String Responder;
	
	public String getResponder() {
		return this.Responder;
	}

	public void setResponder(String Responder) {
		this.Responder = Responder;
	}

/////////////////////////////////////////////////////////////////////
	public String getCategory() {
		return this.Category;
	}

	public void setCategory(String Category) {
		this.Category = Category;
	}

	@Column(nullable = true,unique = false)
	public String Category;

/////////////////////////////////////////////////////////////////////

}
