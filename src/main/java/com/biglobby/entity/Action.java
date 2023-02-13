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
@Table(name = "tbl_action")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action {

	public Action(Long id) {
		super();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "tbl_action")
	private String action;

	@Column(name = "description")
	private String description;

	/**
	 * Relationship
	 */

	@JsonIgnore
	@OneToMany(mappedBy = "action")
	private List<BCoinHistory> bCoinHistories;

	@JsonIgnore
	@OneToMany(mappedBy = "action")
	private List<BServiceHistory> bServiceHistories;

	@JsonIgnore
	@OneToMany(mappedBy = "action")
	private List<DisplayFeeHistory> displayFeeHistories;

	@JsonIgnore
	@OneToMany(mappedBy = "action")
	private List<ProductHistory> productHistories;

}
