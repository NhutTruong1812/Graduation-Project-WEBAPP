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
@Table(name="tbl_bservice_price")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BServicePrice {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="price")
	private Double price;
	
	@Column(name="price_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date priceDate;
	
	@ManyToOne
	@JoinColumn(name="price_by")
	private User priceBy;
	
	@ManyToOne
	@JoinColumn(name="id_bservice")
	private BService bservice;
	
	@Column(name="note")
	private String note;
}
