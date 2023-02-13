package com.biglobby.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_order")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_buyer")
	private User buyer;

	@Column(name = "fullname")
	private String fullname;

	@Column(name = "phonenum")
	private String phonenum;

	@Column(name = "address")
	private String address;

	@Column(name = "add_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date addDate;

	@Column(name = "note_buyer")
	private String noteBuyer;

	@Column(name = "note_saler")
	private String noteSaler;

	@ManyToOne
	@JoinColumn(name = "id_status")
	private Status status;

	@ManyToOne
	@JoinColumn(name = "id_saler")
	private User saler;

	/**
	 * Relationship
	 */

	@JsonIgnore
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetails;

	// @OneToMany(fetch = FetchType.EAGER)
	public Double getSumm() {
		Double sumtemp = 0.0;
		try {
			if (this.getOrderDetails() != null) {
				if (this.getOrderDetails().size() > 0) {
					List<OrderDetail> listod = this.getOrderDetails();
					for (OrderDetail od : listod) {
						sumtemp = sumtemp + (od.getPrice() * od.getQuantity());
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getSumm: " + e);
		}
		return sumtemp;
	}
}
