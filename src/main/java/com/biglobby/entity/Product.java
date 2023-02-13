package com.biglobby.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_product")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5100024136995480012L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@OneToOne
	@JoinColumn(name = "id_card", referencedColumnName = "id")
	private Card card;

	@Column(name = "product")
	private String product;

	@Column(name = "banner")
	private String banner;

	@Column(name = "available")
	private Integer available;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private Double price;

	@Column(name = "price_percent")
	private Double pricePercent;

	@ManyToOne
	@JoinColumn(name = "id_category")
	private Category category;

	/**
	 * Relationship
	 */

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<ProductHistory> productHistories;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<OrderDetail> orderDetails;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<Cart> carts;

}
