package com.biglobby.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_orderdetail")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id_order")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name="id_product")
	private Product product;
	
	@Column(name="quantity")
	private Integer quantity;
	
	@Column(name="price")
	private Double price;

}
