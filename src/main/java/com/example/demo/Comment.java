package com.example.demo;

import java.time.*;
import javax.persistence.*;


@Entity
@Table(name = "comments")
public class Comment {

	
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

	@Column(nullable = true,length=500)	
    public String comment;

    public String getcomment() {
        return this.comment;
    }

    public void setcomment(String comment) {
        this.comment = comment;
    }
	
//////////////////////////////////////////////////////////////////////

    @Column(nullable = true,unique=false,length=45)	
    public String commentor;

    public String getcommentor() {
        return this.commentor;
    }

    public void setcommentor(String commentor) {
        this.commentor = commentor;
    }
////////////////////////////////////////////////////////////////////////
	@Column(nullable = true)	
	public LocalDateTime Create_Date;

    public LocalDateTime getCreate_Date() {
        return this.Create_Date;
    }

    public void setCreate_Date(LocalDateTime Create_Date) {
        this.Create_Date = Create_Date;
    }

//////////////////////////////////////////////////////////////////////

    @ManyToOne
    @JoinColumn(name = "post_id")
	public Post post_id;
}
