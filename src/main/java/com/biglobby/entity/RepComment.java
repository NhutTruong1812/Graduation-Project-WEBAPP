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
@Table(name = "tbl_repcomment")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepComment {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_comment")
	private CommentCard comment;

	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;

	@Column(name = "rep_comment_date")
	private Date repcommentDate;

	@Column(name = "content")
	private String content;

}
