
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
@Table(name = "tbl_myshop")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyShop implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6012397047540587961L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_user", referencedColumnName = "id")
	private User user;

	@Column(name = "intro")
	private String intro;

	@Column(name = "`location`")
	private String location;

	/**
	 * Relationship
	 */

	@JsonIgnore
	@OneToMany(mappedBy = "shop")
	private List<FollowShop> follows;
}
