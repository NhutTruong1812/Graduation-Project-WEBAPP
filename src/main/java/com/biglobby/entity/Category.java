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
@Table(name = "tbl_category")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "category")
	private String category;

	@Column(name = "description")
	private String description;

	@Column(name = "blocked")
	private Boolean blocked;

	@ManyToOne
	@JoinColumn(name = "add_by")
	private User addBy;

	@ManyToOne
	@JoinColumn(name = "id_status")
	private Status status;

	@Column(name = "add_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date addDate;
	
	/**
	 * Relationship
	 */
	
	@JsonIgnore
	@OneToMany(mappedBy = "category")
	private List<Product> products;
}
