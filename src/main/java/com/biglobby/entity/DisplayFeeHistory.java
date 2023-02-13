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
@Table(name = "tbl_displayfee_history")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisplayFeeHistory {

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

	@Column(name = "act_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date actDate;

	@ManyToOne
	@JoinColumn(name = "act_by")
	private User actBy;

	@ManyToOne
	@JoinColumn(name = "id_displayfee")
	private DisplayFee displayFee;

	@ManyToOne
	@JoinColumn(name = "id_action")
	private Action action;

}
