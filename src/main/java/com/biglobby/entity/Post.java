package com.biglobby.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_post")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4314546682810277160L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_card", referencedColumnName = "id")
	private Card card;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "banner")
	private String banner;


	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<Review> reviews;

}
