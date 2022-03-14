package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Request")
public class Request {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long request_id;
	
	@Column(nullable = true)	
	public Long req_user_id;
	
	public Long getReq_user_id() {
		return req_user_id;
	}

	public void setReq_user_id(Long req_user_id) {
		this.req_user_id = req_user_id;
	}

	public String getIdf() {
		return idf;
	}

	public void setIdf(String idf) {
		this.idf = idf;
	}

	@Column(nullable = true)	
	public Long role_id;

	public Long getRequest_id() {
		return request_id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Column(nullable = true)
	private String idf;
	
	@Column(nullable = true)
	private String username;
	
	@Column(nullable = true)
	private String rolename;
	
	

	public void setRequest_id(Long request_id) {
		this.request_id = request_id;
	}


	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}


}
