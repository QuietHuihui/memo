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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;


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
	
	@Column(name="createdDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_fk",nullable=false)
	User user;
	
}
