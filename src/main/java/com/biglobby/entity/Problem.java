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
@Table(name = "tbl_problem")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Problem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "problem")
	private String problem;

	@Column(name = "description")
	private String description;

	/**
	 * Relationship
	 */

	@JsonIgnore
	@OneToMany(mappedBy = "problem")
	private List<ReportCard> reportCards;
}
