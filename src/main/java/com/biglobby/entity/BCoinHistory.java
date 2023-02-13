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
@Table(name="tbl_bcoin_history")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BCoinHistory {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id_user")
	private User user;
	
	@Column(name="quantity")
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name="id_action")
	private Action action;
	
	@Column(name="note")
	private String note;
	
	@Column(name="coin_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date coinDate;
	
	@ManyToOne
	@JoinColumn(name="act_by")
	private User actBy;
}
