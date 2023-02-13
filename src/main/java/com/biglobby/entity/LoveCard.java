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
@Table(name = "tbl_lovecard")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoveCard {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;

	@ManyToOne
	@JoinColumn(name = "id_card")
	private Card card;

	@Column(name = "love_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date loveDate;

}
