package com.example.demo;

import javax.persistence.Column;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;


@Entity
@Table(name = "Responses")
public class Response {

	
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
    
	@ManyToOne
    @JoinColumn(name = "post_id")
	public Post post_id;

	
///////////////////////////////////////////////////////////////////

	@Column(nullable = true,length=500)	
    public String Response;

    public String getResponse() {
        return this.Response;
    }

    public void setResponse(String Response) {
        this.Response = Response;
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

	@Column(nullable = true,unique=false,length=45)	
	public String Responder;
	
	public String getResponder() {
		return this.Responder;
	}

	public void setResponder(String Responder) {
		this.Responder = Responder;
	}
////////////////////////////////////////////////////////////////////////
}
