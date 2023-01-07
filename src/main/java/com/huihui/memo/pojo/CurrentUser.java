package com.huihui.memo.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="currentuser")
public class CurrentUser {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "c_id")
    @SequenceGenerator(name = "c_id", initialValue = 1, allocationSize = 1, sequenceName = "cuser_id")
	@Column(name="id")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_fk",nullable=false)
	User user;

	public CurrentUser(Integer id, User user) {
		this.id = id;
		this.user = user;
	}

	public CurrentUser() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "CurrentUser [id=" + id + ", user=" + user + "]";
	}
	
	
}
