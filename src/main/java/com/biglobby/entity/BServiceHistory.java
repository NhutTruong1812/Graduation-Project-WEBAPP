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
@Table(name="tbl_bservice_history")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BServiceHistory {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id_user")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="id_bservice")
	private BService bservice;
	
	@Column(name="act_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date actDate;
	
	@Column(name="use_days")
	private Integer useDays;
	
	@ManyToOne
	@JoinColumn(name="act_by")
	private User actBy;
	
	@ManyToOne
	@JoinColumn(name="id_action")
	private Action action;
}
