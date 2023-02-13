package com.biglobby.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_news")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_sent")
	private User sentUser;

	@ManyToOne
	@JoinColumn(name = "id_got")
	private User gotUser;

	@Column(name = "`content`")
	private String content;

	@Column(name = "news_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date newsDate;

	@Column(name = "hidden")
	private Boolean hidden;

}
