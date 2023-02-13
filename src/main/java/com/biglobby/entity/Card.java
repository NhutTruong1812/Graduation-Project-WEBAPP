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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_card")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "is_product")
	private Boolean isProduct;

	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;

	@ManyToOne
	@JoinColumn(name = "id_status")
	private Status status;

	@Column(name = "post_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date postAt;

	@Column(name = "hidden")
	private Boolean hidden;

	/**
	 * Relationship
	 */
	@JsonIgnore
	@OneToOne(mappedBy = "card")
	private Post post;

	@JsonIgnore
	@OneToOne(mappedBy = "card")
	private Product product;

	@JsonIgnore
	@OneToMany(mappedBy = "card")
	private List<LoveCard> loves;

	@JsonIgnore
	@OneToMany(mappedBy = "card")
	private List<CommentCard> commments;

	@JsonIgnore
	@OneToMany(mappedBy = "card")
	private List<ShareCard> shares;

	@JsonIgnore
	@OneToMany(mappedBy = "card")
	private List<HideCard> hides;

	@JsonIgnore
	@OneToMany(mappedBy = "card")
	private List<ReportCard> reports;

	@JsonIgnore
	@OneToMany(mappedBy = "card")
	private List<FailHistory> failHistories;

	@JsonIgnore
	@OneToMany(mappedBy = "card")
	private List<CardSubBanner> subBanners;
}
