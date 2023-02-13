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
@Table(name = "tbl_commentcard")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCard {

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

	@Column(name = "comment_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date commentDate;

	@Column(name = "content")
	private String content;

	/**
	 * Relationship
	 */

	@JsonIgnore
	@OneToMany(mappedBy = "comment")
	private List<RepComment> replys;

}
