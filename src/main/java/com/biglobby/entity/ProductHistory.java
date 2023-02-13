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
@Table(name="tbl_product_history")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductHistory {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id_product")
	private Product product;
	
	@Column(name="price")
	private Double price;
	
	@Column(name="quantity")
	private Integer quantity;
	
	@Column(name="act_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date actDate;
	
	@ManyToOne
	@JoinColumn(name="act_by")
	private User actBy;
	
	@ManyToOne
	@JoinColumn(name="id_action")
	private Action action;
}
