package com.biglobby.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_register_active")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterActive implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -321330810345893500L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToOne()
	@JoinColumn(name = "id_user", referencedColumnName = "id")
	private User user;

	@Column(name = "registerDate")
	@Temporal(TemporalType.DATE)
	private Date registerDate;

	@Column(name = "actived")
	private Boolean actived;
}
