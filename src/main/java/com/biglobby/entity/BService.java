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
@Table(name = "tbl_bservice")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BService {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "bservice")
	private String bservice;

	@Column(name = "banner")
	private String banner;

	@Column(name = "description")
	private String description;
	
	@Column(name = "blocked")
	private boolean blocked;

	public BService(Long id, String bservice, String banner, String description, boolean blocked) {
		super();
		this.id = id;
		this.bservice = bservice;
		this.banner = banner;
		this.description = description;
		this.blocked = blocked;
	}

	/**
	 * Relationship
	 */

	@JsonIgnore
	@OneToMany(mappedBy = "bservice")
	private List<MyBService> myBServices;

	@JsonIgnore
	@OneToMany(mappedBy = "bservice")
	private List<BServiceHistory> bServiceHistories;

	@JsonIgnore
	@OneToMany(mappedBy = "bservice")
	private List<BServicePrice> bServicePrices;
	
	@JsonIgnore
	@OneToMany(mappedBy="bservice")
	private List<BServiceSubBanner> subBanners;
}
