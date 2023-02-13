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
@Table(name = "tbl_reportcard")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportCard {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;

	@ManyToOne
	@JoinColumn(name = "id_card")
	private Card card;

	@Column(name = "report_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date reportDate;

	@ManyToOne
	@JoinColumn(name = "id_problem")
	private Problem problem;

	@Column(name = "note")
	private String note;

	@ManyToOne
	@JoinColumn(name="id_status")
	private Status status;
}
