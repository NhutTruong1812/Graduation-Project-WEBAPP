package com.biglobby.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_displayfee")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisplayFee {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "price_from")
	private Double priceFrom;

	@Column(name = "price_to")
	private Double priceTo;

	@Column(name = "fixed_fee")
	private Double fixedFee;

	@Column(name = "percent_fee")
	private Double percentFee;

	@Column(name = "description")
	private String description;

	/**
	 * Relationship
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "displayFee")
	private List<DisplayFeeHistory> displayFeeHistories;

}
