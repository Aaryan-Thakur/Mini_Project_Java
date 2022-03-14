package com.example.demo;

import javax.persistence.*;

@Entity
@Table(name = "users_roles")
public class UR {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rule_id;
	
	@Column(nullable = true)	
	public Long user_id;
	
	@Column(nullable = true)	
	public Long role_id;

	public Long getRule_id() {
		return rule_id;
	}
	
	public void setRule_id(Long rule_id) {
		this.rule_id = rule_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

}
