package com.biglobby.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_my_bcoin")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyBCoin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4542597784586972535L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_user", referencedColumnName = "id")
	private User user;

	@Column(name = "coinnum")
	private Integer coinnum;
}
