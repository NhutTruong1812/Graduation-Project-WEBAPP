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
@Table(name = "tbl_follow_shop")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowShop {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_user_follow")
	private User userFollow;

	@ManyToOne
	@JoinColumn(name = "id_shop")
	private MyShop shop;

	@Column(name = "follow_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date followDate;
}
