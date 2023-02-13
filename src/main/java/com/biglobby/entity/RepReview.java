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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_rep_review")

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RepReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_review")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "content")
    private String content;

    @Column(name = "rep_review_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repReviewAt;
}
