package com.huihui.memo.pojo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

@NamedQuery(name="Note.findByUserId",query="select n from Note n where n.user.id=:uid")

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="note")
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "n_id")
    @SequenceGenerator(name = "n_id", initialValue = 1, allocationSize = 1, sequenceName = "note_id")
	@Column(name="id")
	private Integer id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="content")
	private String content;
	
	@Column(name="status")
	private String status;
	
	@Column(name="createdDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	
	@Column(name="finishedDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date finishedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_fk",nullable=false)
	User user;

	public Note(Integer id, String title, String content, String status, Date createdDate, User user) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.status = status;
		this.createdDate = createdDate;
		this.user = user;
	}

	public Note() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", title=" + title + ", content=" + content + ", createdDate=" + createdDate
				+ ", user=" + user + "]";
	}
	
	
}
