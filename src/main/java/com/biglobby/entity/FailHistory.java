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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_fail_history")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FailHistory {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_card")
	private Card card;

	@Column(name = "fail_reason")
	private String failReason;

	@Column(name = "fail_date")
	private Date failDate;

	@ManyToOne
	@JoinColumn(name = "fail_by")
	private User failBy;
}
