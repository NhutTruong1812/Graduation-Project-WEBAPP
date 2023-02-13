package com.biglobby.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "tbl_user")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	public User(Long id) {
		super();
		this.id = id;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "`password`")
	private String password;

	@Column(name = "fullname")
	private String fullname;

	@Column(name = "email")
	private String email;

	@Column(name = "gender")
	private Boolean gender;

	@Column(name = "phonenum")
	private String phonenum;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "blocked")
	private Boolean blocked;

	@Column(name = "last_login")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	/**
	 * Relationship
	 */

	@JsonIgnore
	@OneToOne(mappedBy = "user")
	private RegisterActive registerActive;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Cart> carts;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Authority> authorities;

	@JsonIgnore
	@OneToOne(mappedBy = "user")
	private MyBCoin bCoin;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<BCoinHistory> bCoinHistories;

	@JsonIgnore
	@OneToMany(mappedBy = "actBy")
	private List<BCoinHistory> actionOnBCoinHistories;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<MyBService> bServices;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<BServiceHistory> bServiceHistories;

	@JsonIgnore
	@OneToMany(mappedBy = "actBy")
	private List<BServiceHistory> actionOnBServiceHistory;

	@JsonIgnore
	@OneToMany(mappedBy = "priceBy")
	private List<BServicePrice> bServicePrices;

	@JsonIgnore
	@OneToOne(mappedBy = "user")
	private MyShop shop;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Address> addresses;

	@JsonIgnore
	@OneToMany(mappedBy = "userFollow")
	private List<FollowShop> followShops;

	@JsonIgnore
	@OneToMany(mappedBy = "actBy")
	private List<DisplayFeeHistory> displayFeeHistories;

	@JsonIgnore
	@OneToMany(mappedBy = "sentUser")
	private List<News> newsSents;

	@JsonIgnore
	@OneToMany(mappedBy = "gotUser")
	private List<News> newsGots;

	@JsonIgnore
	@OneToMany(mappedBy = "addBy")
	private List<Category> categories;

	@JsonIgnore
	@OneToMany(mappedBy = "actBy")
	private List<ProductHistory> productHistories;

	@JsonIgnore
	@OneToMany(mappedBy = "buyer")
	private List<Order> orderOfBuy;

	@JsonIgnore
	@OneToMany(mappedBy = "saler")
	private List<Order> orderOfSale;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<LoveCard> loveCards;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<CommentCard> commentCards;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<RepComment> replys;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<ShareCard> shareCards;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<HideCard> hideCards;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<ReportCard> reportCards;

	@JsonIgnore
	@OneToMany(mappedBy = "failBy")
	private List<FailHistory> failHistories;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Card> cards;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Review> reviews;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<RepReview> repReviews;
}
